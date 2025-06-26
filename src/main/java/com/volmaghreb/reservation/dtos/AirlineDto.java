package com.volmaghreb.reservation.dtos;

import lombok.Data;

@Data
public class AirlineDto {
    private Long id;
    private String name;
    private String code;
    private String country;
}
