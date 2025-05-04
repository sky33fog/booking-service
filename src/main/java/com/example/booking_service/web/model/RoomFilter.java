package com.example.booking_service.web.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RoomFilter {

    @NotNull(message = "Размер страницы вывода должен быть указан. Пример: pageSize=4")
    private Integer pageSize;

    @NotNull(message = "Номер страницы вывода должен быть указан. Пример: pageNumber=0")
    private Integer pageNumber;

    private Long roomId;

    private String description;

    private Integer guests;

    private String arrival;

    private String departure;

    private Long hotelId;

}
