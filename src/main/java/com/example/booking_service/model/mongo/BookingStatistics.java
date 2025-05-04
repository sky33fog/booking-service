package com.example.booking_service.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "booking_stat")
@AllArgsConstructor
@ToString
@Getter
public class BookingStatistics {

    @Id
    private String id;

    private String userId;

    private String roomId;

    private String arrival;

    private String departure;

    private String bookingDateTime;
}
