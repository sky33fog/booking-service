package com.example.booking_service.repository;

import com.example.booking_service.model.mongo.UserStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserStatisticsRepository extends MongoRepository<UserStatistics, String> {
}
