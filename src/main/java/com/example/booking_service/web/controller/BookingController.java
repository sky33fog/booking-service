package com.example.booking_service.web.controller;

import com.example.booking_service.mapper.BookingMapper;
import com.example.booking_service.model.Booking;
import com.example.booking_service.model.kafka.BookingRoomEvent;
import com.example.booking_service.service.BookingService;
import com.example.booking_service.web.model.BookingRequest;
import com.example.booking_service.web.model.BookingResponse;
import com.example.booking_service.web.model.ResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    private final BookingMapper bookingMapper;

    private final KafkaTemplate<String, BookingRoomEvent> kafkaTemplateBookingRoom;

    @Value("${app.kafka.kafkaBookingTopic}")
    private String kafkaBookingTopic;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<BookingResponse> create(@RequestBody BookingRequest request) {

        Booking booking = bookingService.bookRoom(bookingMapper.requestToBooking(request));

        kafkaTemplateBookingRoom.send(kafkaBookingTopic, new BookingRoomEvent(
                booking.getUser().getName(),
                booking.getRoom().getId(),
                booking.getArrival().toString(),
                booking.getDeparture().toString())
        );
        return ResponseEntity
                .ok(bookingMapper.bookingToResponse(booking));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseList> findAll() {
        return ResponseEntity.ok(bookingMapper.bookingListToResponseList(bookingService.findAll()));
    }
}
