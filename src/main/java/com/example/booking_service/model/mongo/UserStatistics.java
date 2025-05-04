package com.example.booking_service.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_stat")
@AllArgsConstructor
@ToString
@Getter
public class UserStatistics {

    @Id
    private String id;

    private String userId;

    private String createDateTime;
}
