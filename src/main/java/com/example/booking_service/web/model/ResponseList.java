package com.example.booking_service.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseList {
    List<Object> responseList = new ArrayList<>();
}
