package com.example.booking_service.repository;

import com.example.booking_service.model.Hotel;
import com.example.booking_service.web.model.HotelFilter;
import org.springframework.data.jpa.domain.Specification;

public interface HotelSpecification {

    static Specification<Hotel> withFilter(HotelFilter filter) {
        return Specification
                .where(byId(filter.getId()))
                .and(byName(filter.getName()))
                .and(byTitle(filter.getTitle()))
                .and(byCity(filter.getCity()))
                .and(byAddress(filter.getAddress()))
                .and(byDistanceFromCenter(filter.getDistanceFromCenter()))
                .and(byRating(filter.getRating()))
                .and(byNumberOfRating(filter.getNumberOfRating()));
    }

    static Specification<Hotel> byId(Long id) {
        return (root, query, criteriaBuilder) -> {
            if(id == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("id"), id);
        };
    }

    static Specification<Hotel> byName(String name) {
        return (root, query, criteriaBuilder) -> {
            if(name == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("name"), name);
        };
    }

    static Specification<Hotel> byTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if(title == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("title"), title);
        };
    }

    static Specification<Hotel> byCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if(city == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("city"), city);
        };
    }

    static Specification<Hotel> byAddress(String address) {
        return (root, query, criteriaBuilder) -> {
            if(address == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("address"), address);
        };
    }

    static Specification<Hotel> byDistanceFromCenter(String distanceFromCenter) {
        return (root, query, criteriaBuilder) -> {
            if(distanceFromCenter == null) {
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("distanceFromCenter"), distanceFromCenter);
        };
    }

    static Specification<Hotel> byRating(Float rating) {
        return (root, query, criteriaBuilder) -> {
            if(rating == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), rating);
        };
    }

    static Specification<Hotel> byNumberOfRating(Integer numberOfRating) {
        return (root, query, criteriaBuilder) -> {
            if(numberOfRating == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("numberOfRating"), numberOfRating);
        };
    }
}
