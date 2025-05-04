package com.example.booking_service.mapper;

import com.example.booking_service.model.Hotel;
import com.example.booking_service.web.model.HotelResponse;
import com.example.booking_service.web.model.HotelUpsertRequest;
import com.example.booking_service.web.model.ResponseFilterList;
import com.example.booking_service.web.model.ResponseList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    Hotel requestToHotel(HotelUpsertRequest request);

    @Mapping(source = "hotelId", target = "id")
    Hotel requestToHotel(Long hotelId, HotelUpsertRequest request);

    HotelResponse hotelToResponse(Hotel hotel);

    default ResponseList hotelListToResponseList(List<Hotel> hotels) {
        ResponseList responseList = new ResponseList();

        responseList.setResponseList(hotels
                .stream()
                .map(this::hotelToResponse)
                .collect(Collectors.toList()));

        return responseList;
    }

    default ResponseFilterList hotelListToResponseFilterList(List<Hotel> hotels) {
        ResponseFilterList responseFilterList = new ResponseFilterList();

        responseFilterList.setList(hotels
                .stream()
                .map(this::hotelToResponse)
                .collect(Collectors.toList()));

        responseFilterList.setAmount(hotels.size());

        return responseFilterList;
    }
}
