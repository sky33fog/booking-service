package com.example.booking_service.repository;

import com.example.booking_service.exception.IncorrectPeriodException;
import com.example.booking_service.model.Room;
import com.example.booking_service.web.model.RoomFilter;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface RoomSpecification {

    static Specification<Room> withFilter(RoomFilter filter) {
        return Specification
                .where(byRoomId(filter.getRoomId()))
                .and(byDescription(filter.getDescription()))
                .and(byAmountGuests(filter.getGuests()))
                .and(byDates(filter.getArrival(), filter.getDeparture()))
                .and(byHotelId(filter.getHotelId()));
    }

    static Specification<Room> byRoomId(Long roomId) {
        return (root, query, criteriaBuilder) -> {
            if(roomId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("id"), roomId);
        };
    }

    static Specification<Room> byDescription(String description) {
        return (root, query, criteriaBuilder) -> {
            if(description == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("description"), description);
        };
    }

    static Specification<Room> byAmountGuests(Integer guests) {
        return (root, query, criteriaBuilder) -> {
            if(guests == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("maxGuests"), guests);
        };
    }

    static Specification<Room> byDates(String arrival, String departure) {

        return (root, query, criteriaBuilder) -> {
            if (arrival == null || departure == null) {
                return null;
            }

            Subquery<LocalDate> subquery = query.subquery(LocalDate.class);
            Root<Room> subRoot = subquery.from(Room.class);
            Join<Room, LocalDate> datesJoin = subRoot.join("bookingDates");

            subquery.select(datesJoin)
                    .where(subRoot.get("id").in(root.get("id")), datesJoin.in(getDates(arrival, departure)));

            return criteriaBuilder.not(criteriaBuilder.exists(subquery));
        };
    }

    static Specification<Room> byHotelId(Long hotelId) {
        return (root, query, criteriaBuilder) -> {
            if(hotelId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("hotel").get("id"), hotelId);
        };
    }

    private static List<LocalDate> getDates(String arrival, String departure) {

        LocalDate arrLd = LocalDate.parse(arrival);
        LocalDate depLd = LocalDate.parse(departure);

        if(arrLd.isAfter(depLd) || arrLd.isEqual(depLd)) {
            throw  new IncorrectPeriodException("Incorrect dates.");
        }

        List<LocalDate> bookingDates = new ArrayList<>();

        do {
            bookingDates.add(arrLd);
            arrLd = arrLd.plusDays(1);
        } while(!arrLd.isEqual(depLd));

         return bookingDates;
    }
}
