package com.example.booking_service.service;

import com.example.booking_service.exception.EntityNotFoundException;
import com.example.booking_service.exception.IncorrectRateException;
import com.example.booking_service.model.Hotel;
import com.example.booking_service.repository.HotelRepository;
import com.example.booking_service.repository.HotelSpecification;
import com.example.booking_service.util.BeanUtils;
import com.example.booking_service.web.model.HotelFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public List<Hotel> findAll(HotelFilter filter) {
        return hotelRepository.findAll(HotelSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }

    public Hotel findById(Long id) {
        return hotelRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Entity with id: {0}, not found!", id)));
    }

    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel update(Hotel hotel) {
        Hotel existedHotel = findById(hotel.getId());
        BeanUtils.copyNonNullProperties(hotel, existedHotel);
        return hotelRepository.save(existedHotel);
    }

    public void deleteById(Long id) {
        hotelRepository.deleteById(id);
    }

    public Hotel rateHotel(Long id, Integer newRate) {
        if(newRate < 1 || newRate > 5) {
            throw new IncorrectRateException("The rate must be integer value between 1 and 5.");
        }
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Entity with id: {0}, not found!", id)));


       return hotelRepository.save(calculateRating(hotel, newRate));
    }

    private Hotel calculateRating(Hotel hotel, Integer newRate) {
        float totalRating;
        float newRating;

        if(hotel.getNumberOfRating() == 0) {
            newRating = newRate;
        } else if(hotel.getNumberOfRating() == 1) {
            newRating = (hotel.getRating() + newRate) / 2;
        } else {
            totalRating = hotel.getRating() * hotel.getNumberOfRating();
            totalRating = totalRating - hotel.getRating() + newRate;
            newRating = totalRating / hotel.getNumberOfRating();
        }

        newRating = (Math.round(newRating * 10.0f) / 10.0f);

        hotel.setRating(newRating);
        hotel.setNumberOfRating(hotel.getNumberOfRating() + 1);

        return hotel;
    }
}
