package com.example.booking_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Integer maxGuests;

    @ElementCollection
    @CollectionTable(name = "room_dates", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "date")
    private List<LocalDate> bookingDates = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany
    private List<Booking> bookings = new ArrayList<>();
}
