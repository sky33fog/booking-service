package com.example.booking_service.web.controller;

import com.example.booking_service.mapper.RoomMapper;
import com.example.booking_service.service.RoomService;
import com.example.booking_service.web.model.ResponseFilterList;
import com.example.booking_service.web.model.RoomFilter;
import com.example.booking_service.web.model.RoomResponse;
import com.example.booking_service.web.model.RoomUpsertRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomMapper roomMapper;

    private final RoomService roomService;

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(roomMapper.roomToResponse(roomService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoomResponse> create(@RequestBody RoomUpsertRequest request) {
        return ResponseEntity.ok(roomMapper.roomToResponse(roomService.save(roomMapper.requestToRoom(request))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoomResponse> update(@PathVariable Long id,
                                               @RequestBody RoomUpsertRequest request) {
        return ResponseEntity.ok(roomMapper.roomToResponse(roomService.update(roomMapper.requestToRoom(id, request))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ResponseFilterList>withFilter(@Valid RoomFilter filter) {
        return ResponseEntity.ok(roomMapper.roomListToResponseFilterList(roomService.findALl(filter)));
    }

}
