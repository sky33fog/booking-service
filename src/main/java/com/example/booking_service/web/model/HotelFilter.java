package com.example.booking_service.web.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HotelFilter {

    @NotNull(message = "Размер страницы вывода должен быть указан. Пример: pageSize=4")
    private Integer pageSize;

    @NotNull(message = "Номер страницы вывода должен быть указан. Пример: pageNumber=0")
    private Integer pageNumber;

    private Long id;

    private String name;

    private String title;

    private String city;

    private String address;

    private String distanceFromCenter;

    private Float rating;

    private Integer numberOfRating;

}
