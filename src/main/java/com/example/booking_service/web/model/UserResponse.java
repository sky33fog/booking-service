package com.example.booking_service.web.model;

import com.example.booking_service.model.RoleType;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {

    private String name;

    private String password;

    private String email;

    private List<RoleType> roles;
}
