package com.volmaghreb.reservation.mappers;

import com.volmaghreb.reservation.dtos.AirportDto;
import com.volmaghreb.reservation.entities.Airport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    AirportDto toDto(Airport airport);
    Airport toEntity(AirportDto airportDto);
}
