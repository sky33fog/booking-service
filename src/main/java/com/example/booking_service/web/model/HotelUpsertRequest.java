package com.example.booking_service.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HotelUpsertRequest {

    @NotBlank(message = "The name must be specified.")
    private String name;

    private String title;

    private String city;

    @NotBlank(message = "The city must be specified.")
    private String address;

    private Integer distanceFromCenter;
}
