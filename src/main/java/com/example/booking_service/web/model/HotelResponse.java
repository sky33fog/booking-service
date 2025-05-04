package com.example.booking_service.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HotelResponse {

    private Long id;

    private String name;

    private String title;

    private String city;

    private String address;

    private Integer distanceFromCenter;

    private Float rating;

    private List<RoomResponse> rooms = new ArrayList<>();
}
