package com.volmaghreb.reservation.services;

import com.volmaghreb.reservation.dtos.FlightDto;
import com.volmaghreb.reservation.entities.Flight;
import com.volmaghreb.reservation.enums.FlightStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightService {
    
    List<Flight> getAllFlights();
    
    Optional<Flight> getFlightById(Long id);

    Optional<FlightDto> getFlightDtoById(Long id);
    
    Optional<Flight> getFlightByFlightNumber(String flightNumber);
    
    Flight saveFlight(Flight flight);
    
    void deleteFlight(Long id);
    
    List<Flight> getFlightsByStatus(FlightStatus status);
    
    List<Flight> searchFlights(String searchTerm);
    
    // Enhanced search method for web interface
    List<Flight> searchFlights(Long originId, Long destinationId, LocalDate departureDate);
    
    // Enhanced search method with seat availability check
    List<Flight> searchFlightsWithAvailability(Long originId, Long destinationId, LocalDate departureDate, String travelClass, Integer requiredSeats);
    
    List<Flight> getFlightsByRoute(Long originId, Long destinationId);
    
    List<Flight> getFlightsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    String generateFlightNumber();
    
    boolean isFlightNumberUnique(String flightNumber);
    
    Flight updateFlight(Long id, Flight flightData);
}
