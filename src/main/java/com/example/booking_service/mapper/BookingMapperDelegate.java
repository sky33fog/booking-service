package com.example.booking_service.mapper;

import com.example.booking_service.model.Booking;
import com.example.booking_service.service.RoomService;
import com.example.booking_service.service.UserService;
import com.example.booking_service.web.model.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class BookingMapperDelegate implements BookingMapper {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;


    @Override
    public Booking requestToBooking(BookingRequest request) {
        Booking newBooking = new Booking();

        newBooking.setArrival(request.getArrival());
        newBooking.setDeparture(request.getDeparture());
        newBooking.setRoom(roomService.findById(request.getRoomId()));

        return newBooking;
    }
}
