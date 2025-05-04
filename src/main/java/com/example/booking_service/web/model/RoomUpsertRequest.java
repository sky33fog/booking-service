package com.example.booking_service.web.model;

import lombok.Data;

@Data
public class RoomUpsertRequest {

    private String name;

    private String description;

    private Integer maxGuests;

    private Long hotelId;
}
