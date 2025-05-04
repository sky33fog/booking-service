package com.example.booking_service.web.controller;

import com.example.booking_service.mapper.HotelMapper;
import com.example.booking_service.model.Hotel;
import com.example.booking_service.service.HotelService;
import com.example.booking_service.web.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    private final HotelMapper hotelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelMapper.hotelToResponse(hotelService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> create(@RequestBody @Valid HotelUpsertRequest request) {
        return ResponseEntity.ok(hotelMapper.hotelToResponse(hotelService.save(hotelMapper.requestToHotel(request))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> update(@PathVariable Long id,
                                                @RequestBody HotelUpsertRequest request) {
        Hotel updatedHotel = hotelService.update(hotelMapper.requestToHotel(id, request));

        return ResponseEntity.ok(hotelMapper.hotelToResponse(updatedHotel));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        hotelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ResponseFilterList> findAll(@Valid HotelFilter filter) {
        return ResponseEntity.ok(hotelMapper.hotelListToResponseFilterList(hotelService.findAll(filter)));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<HotelResponse> rateHotel(@RequestBody RateRequest request) {
        return ResponseEntity.ok(hotelMapper.hotelToResponse(hotelService
                .rateHotel(request.getHotelId(), request.getRate())));
    }
}
