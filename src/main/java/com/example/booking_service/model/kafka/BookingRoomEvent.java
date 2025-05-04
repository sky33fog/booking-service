package com.example.booking_service.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingRoomEvent {

    private String userId;

    private Long roomId;

    private String arrival;

    private String departure;
}
