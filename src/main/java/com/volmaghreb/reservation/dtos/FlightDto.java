package com.volmaghreb.reservation.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class FlightDto {
    private Long id;
    private String flightNumber;
    private AirportDto originAirport;
    private AirportDto destinationAirport;
    private AirportDto departureAirport; // For reservation-detail.html compatibility
    private AirportDto arrivalAirport;   // For reservation-detail.html compatibility
    private AirlineDto airline;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private LocalDateTime departureTime; // For reservation-detail.html compatibility
    private LocalDateTime arrivalTime;   // For reservation-detail.html compatibility
    private BigDecimal price;
    private BigDecimal firstClassPrice;
    private BigDecimal businessClassPrice;
    private BigDecimal economyClassPrice;
    private int availableSeats;
    private String travelClass;
    private String duration;
    
    // Calculate flight duration in a human-readable format
    public String getFlightDuration() {
        if (departureDateTime == null || arrivalDateTime == null) {
            return "N/A";
        }
        
        Duration flightDuration = Duration.between(departureDateTime, arrivalDateTime);
        long hours = flightDuration.toHours();
        long minutes = flightDuration.toMinutesPart();
        
        if (hours > 0) {
            return String.format("%dh %02dm", hours, minutes);
        } else {
            return String.format("%dm", minutes);
        }
    }
    
    // Getter for duration to maintain compatibility
    public String getDuration() {
        if (duration != null) {
            return duration;
        }
        return getFlightDuration();
    }
}
