package com.example.booking_service.service;

import com.example.booking_service.model.mongo.BookingStatistics;
import com.example.booking_service.model.mongo.UserStatistics;
import com.example.booking_service.repository.BookingStatisticsRepository;
import com.example.booking_service.repository.UserStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final UserStatisticsRepository userStatisticsRepository;

    private final BookingStatisticsRepository bookingStatisticsRepository;


    public String getStatistics() {
        List<UserStatistics> userStatList = userStatisticsRepository.findAll();
        List<BookingStatistics> bookingStatList = bookingStatisticsRepository.findAll();

        StringBuilder csvContent = new StringBuilder();

        csvContent.append("UserID,CreateDateTime\n");
        userStatList.forEach(data -> csvContent.append(String.join(",",
                data.getUserId(),
                data.getCreateDateTime(),
                "\n")));

        if(!bookingStatList.isEmpty()) {
            csvContent.append("UserID,RoomID,Arrival,Departure,BookingDateTime\n");
            bookingStatList.forEach(data -> csvContent.append(String.join(",",
                    data.getUserId(),
                    data.getRoomId(),
                    data.getArrival(),
                    data.getDeparture(),
                    data.getBookingDateTime(),
                    "\n")));

        }
        return csvContent.toString();
    }
}
