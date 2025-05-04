package com.example.booking_service.mapper;

import com.example.booking_service.model.Booking;
import com.example.booking_service.web.model.BookingRequest;
import com.example.booking_service.web.model.BookingResponse;
import com.example.booking_service.web.model.ResponseList;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(BookingMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    Booking requestToBooking(BookingRequest request);

    BookingResponse bookingToResponse(Booking booking);

    default ResponseList bookingListToResponseList(List<Booking> bookingList) {
        ResponseList responseList = new ResponseList();

        responseList.setResponseList(bookingList
                .stream()
                .map(this::bookingToResponse)
                .collect(Collectors.toList())
        );
        return responseList;
    }
}
