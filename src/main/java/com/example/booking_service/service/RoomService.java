package com.example.booking_service.service;

import com.example.booking_service.exception.EntityNotFoundException;
import com.example.booking_service.exception.IncorrectPeriodException;
import com.example.booking_service.model.Room;
import com.example.booking_service.repository.RoomRepository;
import com.example.booking_service.repository.RoomSpecification;
import com.example.booking_service.util.BeanUtils;
import com.example.booking_service.web.model.RoomFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<Room> findALl(RoomFilter filter) {
        return roomRepository.findAll(RoomSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }

    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(MessageFormat.format("Entity with id: {0}, not found", id)));
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public Room update(Room room) {
        Room existedRoom = findById(room.getId());
        BeanUtils.copyNonNullProperties(room, existedRoom);
        return roomRepository.save(existedRoom);
    }

    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    private static List<LocalDate> getDates(String arrival, String departure) {

        LocalDate arrLd = LocalDate.parse(arrival);
        LocalDate depLd = LocalDate.parse(departure);

        if(arrLd.isAfter(depLd) || arrLd.isEqual(depLd)) {
            throw  new IncorrectPeriodException("Incorrect dates.");
        }

        List<LocalDate> bookingDates = new ArrayList<>();

        do {
            bookingDates.add(arrLd);
            arrLd = arrLd.plusDays(1);
        } while(!arrLd.isEqual(depLd));

        return bookingDates;
    }
}
