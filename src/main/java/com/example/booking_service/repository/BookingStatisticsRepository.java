package com.example.booking_service.repository;

import com.example.booking_service.model.mongo.BookingStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingStatisticsRepository extends MongoRepository<BookingStatistics, String> {
}
