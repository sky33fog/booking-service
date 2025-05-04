package com.example.booking_service.web.model;

import lombok.Data;

@Data
public class UserUpsertRequest {

    private String name;

    private String password;

    private String email;

    /// надо ли?
//    private String role;
}
