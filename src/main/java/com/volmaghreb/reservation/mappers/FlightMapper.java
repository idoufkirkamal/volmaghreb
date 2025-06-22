package com.volmaghreb.reservation.mappers;

import com.volmaghreb.reservation.dtos.FlightDto;
import com.volmaghreb.reservation.entities.Flight;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    FlightDto toDto(Flight flight);
    Flight toEntity(FlightDto flightDto);
}
