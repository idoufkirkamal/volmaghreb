package com.volmaghreb.reservation.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FlightDto {
    private Long id;
    private String flightNumber;
    private String departureAirport;
    private String arrivalAirport;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private double price;
    private int availableSeats;
}
