package com.volmaghreb.reservation.services.impl;

import com.volmaghreb.reservation.entities.Flight;
import com.volmaghreb.reservation.enums.FlightStatus;
import com.volmaghreb.reservation.repositories.FlightRepository;
import com.volmaghreb.reservation.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class FlightServiceImpl implements FlightService {
    
    @Autowired
    private FlightRepository flightRepository;
    
    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
    
    @Override
    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }
    
    @Override
    public Optional<Flight> getFlightByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }
    
    @Override
    public Flight saveFlight(Flight flight) {
        // Generate flight number if not provided
        if (flight.getFlightNumber() == null || flight.getFlightNumber().isEmpty()) {
            flight.setFlightNumber(generateFlightNumber());
        }
        return flightRepository.save(flight);
    }
    
    @Override
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }
    
    @Override
    public List<Flight> getFlightsByStatus(FlightStatus status) {
        return flightRepository.findByStatus(status);
    }
    
    @Override
    public List<Flight> searchFlights(String searchTerm) {
        return flightRepository.searchFlights(searchTerm, searchTerm, searchTerm);
    }
    
    @Override
    public List<Flight> getFlightsByRoute(Long originId, Long destinationId) {
        return flightRepository.findByOriginAndDestination(originId, destinationId);
    }
    
    @Override
    public List<Flight> getFlightsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return flightRepository.findByDepartureDateTimeBetween(startDate, endDate);
    }
    
    @Override
    public String generateFlightNumber() {
        String prefix = "VOL";
        Random random = new Random();
        String flightNumber;
        
        do {
            int number = 1000 + random.nextInt(9000); // Generate 4-digit number
            flightNumber = prefix + number;
        } while (flightRepository.existsByFlightNumber(flightNumber));
        
        return flightNumber;
    }
    
    @Override
    public boolean isFlightNumberUnique(String flightNumber) {
        return !flightRepository.existsByFlightNumber(flightNumber);
    }
}
