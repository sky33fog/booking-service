package com.example.booking_service.service;

import com.example.booking_service.exception.ErrorBookingException;
import com.example.booking_service.exception.IncorrectPeriodException;
import com.example.booking_service.model.Booking;
import com.example.booking_service.repository.BookingRepository;
import com.example.booking_service.security.AppUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final UserService userService;

    public Booking bookRoom(Booking booking) {

        LocalDate arrival = booking.getArrival();
        LocalDate departure = booking.getDeparture();

        if(arrival.isAfter(departure) || arrival.isEqual(departure)) {
            throw  new IncorrectPeriodException("Incorrect dates.");
        }

        List<LocalDate> bookingDatesRequest = new ArrayList<>();

         do {
            bookingDatesRequest.add(arrival);
            arrival = arrival.plusDays(1);
        } while(!arrival.isEqual(departure));

        bookingDatesRequest.forEach(d -> {
            if(booking.getRoom().getBookingDates().contains(d)) {
                throw new ErrorBookingException("Booking in specified dates already exists.");
            }
        });

        AppUserPrincipal appUserPrincipal = (AppUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        booking.setUser(userService.findByName(appUserPrincipal.getUsername()));
        booking.getRoom().getBookingDates().addAll(bookingDatesRequest);

        return bookingRepository.save(booking);
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }
}
