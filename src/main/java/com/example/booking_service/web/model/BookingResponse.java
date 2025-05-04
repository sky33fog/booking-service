package com.example.booking_service.web.model;

import lombok.Data;


@Data
public class BookingResponse {

    private Long id;

    private String arrival;

    private String departure;

    private RoomResponse room;

    private UserResponse user;
}
