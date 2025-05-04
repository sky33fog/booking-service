package com.example.booking_service.web.model;

import lombok.Data;

@Data
public class RateRequest {

    private Long hotelId;

    private Integer rate;
}
