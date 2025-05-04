package com.example.booking_service.mapper;

import com.example.booking_service.model.Hotel;
import com.example.booking_service.model.Room;
import com.example.booking_service.web.model.ResponseFilterList;
import com.example.booking_service.web.model.RoomResponse;
import com.example.booking_service.web.model.RoomUpsertRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;


@DecoratedWith(RoomMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    Room requestToRoom(RoomUpsertRequest request);

    @Mapping(source = "roomId", target = "id")
    Room requestToRoom(Long roomId, RoomUpsertRequest request);

    RoomResponse roomToResponse(Room room);

    default ResponseFilterList roomListToResponseFilterList(List<Room> rooms) {
        ResponseFilterList responseFilterList = new ResponseFilterList();

        responseFilterList.setList(rooms
                .stream()
                .map(this::roomToResponse)
                .collect(Collectors.toList()));

        responseFilterList.setAmount(rooms.size());

        return responseFilterList;
    }

}
