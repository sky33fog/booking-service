package com.example.booking_service.mapper;

import com.example.booking_service.model.Room;
import com.example.booking_service.service.HotelService;
import com.example.booking_service.web.model.RoomUpsertRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class RoomMapperDelegate implements RoomMapper {

    @Autowired
    private HotelService hotelService;

    @Override
    public Room requestToRoom(RoomUpsertRequest request) {
        Room newRoom = new Room();

        newRoom.setName(request.getName());
        newRoom.setDescription(request.getDescription());
        newRoom.setMaxGuests(request.getMaxGuests());
        newRoom.setHotel(hotelService.findById(request.getHotelId()));
        return newRoom;
    }

    @Override
    public Room requestToRoom(Long id, RoomUpsertRequest request) {
        Room newRoom = requestToRoom(request);
        newRoom.setId(id);
        return newRoom;
    }

}
