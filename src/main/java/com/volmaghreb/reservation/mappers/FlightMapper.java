package com.volmaghreb.reservation.mappers;

import com.volmaghreb.reservation.dtos.FlightDto;
import com.volmaghreb.reservation.entities.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AirportMapper.class})
public interface FlightMapper {
    @Mapping(target = "departureAirport", source = "originAirport")
    @Mapping(target = "arrivalAirport", source = "destinationAirport")
    @Mapping(target = "departureTime", source = "departureDateTime")
    @Mapping(target = "arrivalTime", source = "arrivalDateTime")
    @Mapping(target = "price", source = "economyClassPrice")
    @Mapping(target = "airline", expression = "java(createDefaultAirline())")
    @Mapping(target = "travelClass", constant = "Economy")
    @Mapping(target = "duration", expression = "java(flight.getFlightDuration())")
    FlightDto toDto(Flight flight);
    
    Flight toEntity(FlightDto flightDto);
    
    default com.volmaghreb.reservation.dtos.AirlineDto createDefaultAirline() {
        com.volmaghreb.reservation.dtos.AirlineDto airline = new com.volmaghreb.reservation.dtos.AirlineDto();
        airline.setId(1L);
        airline.setName("Volmaghreb Airlines");
        airline.setCode("VM");
        airline.setCountry("Morocco");
        return airline;
    }
}
