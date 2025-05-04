package com.example.booking_service.web.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {

    private LocalDate arrival;

    private LocalDate departure;

    private Long roomId;
}
