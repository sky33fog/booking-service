package com.example.booking_service.mapper;

import com.example.booking_service.model.User;
import com.example.booking_service.web.model.ResponseList;
import com.example.booking_service.web.model.UserResponse;
import com.example.booking_service.web.model.UserUpsertRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User requestToUser(UserUpsertRequest request);

    UserResponse userToResponse(User user);

    default ResponseList userListToResponseList(List<User> users) {
        ResponseList responseList = new ResponseList();

        responseList.setResponseList(users
               .stream()
               .map(this::userToResponse)
               .collect(Collectors.toList()));
       return responseList;
    }
}
