package com.example.booking_service.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomResponse {

    private Long id;

    private String name;

    private String description;

    private Integer maxGuests;

    private List<String> bookingDates = new ArrayList<>();
}
