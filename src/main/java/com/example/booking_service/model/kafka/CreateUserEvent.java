package com.example.booking_service.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserEvent {

    private String userId;
}
