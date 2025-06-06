package com.volmaghreb.reservation.services.impl;

import com.volmaghreb.reservation.entities.Flight;
import com.volmaghreb.reservation.enums.FlightStatus;
import com.volmaghreb.reservation.repositories.FlightRepository;
import com.volmaghreb.reservation.services.FlightService;
import com.volmaghreb.reservation.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class FlightServiceImpl implements FlightService {
    
    @Autowired
    private FlightRepository flightRepository;
    
    @Autowired
    private SeatService seatService;
    
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
    @Transactional
    public Flight saveFlight(Flight flight) {
        // Generate flight number if not provided
        if (flight.getFlightNumber() == null || flight.getFlightNumber().isEmpty()) {
            flight.setFlightNumber(generateFlightNumber());
        }
        
        // Save the flight first
        Flight savedFlight = flightRepository.save(flight);
        
        // Create seats for the flight
        seatService.createSeatsForFlight(savedFlight);
        
        return savedFlight;
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
    public List<Flight> searchFlights(Long originId, Long destinationId, LocalDate departureDate) {
        List<Flight> flights = getAllFlights();
        
        // Filter by origin airport if provided
        if (originId != null) {
            flights = flights.stream()
                    .filter(flight -> flight.getOriginAirport() != null && 
                                    flight.getOriginAirport().getId().equals(originId))
                    .toList();
        }
        
        // Filter by destination airport if provided
        if (destinationId != null) {
            flights = flights.stream()
                    .filter(flight -> flight.getDestinationAirport() != null && 
                                    flight.getDestinationAirport().getId().equals(destinationId))
                    .toList();
        }
        
        // Filter by departure date if provided
        if (departureDate != null) {
            flights = flights.stream()
                    .filter(flight -> flight.getDepartureDateTime() != null && 
                                    flight.getDepartureDateTime().toLocalDate().equals(departureDate))
                    .toList();
        }
        
        return flights;
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
    
    @Override
    @Transactional
    public Flight updateFlight(Long id, Flight flightData) {
        Optional<Flight> existingFlightOpt = flightRepository.findById(id);
        if (existingFlightOpt.isEmpty()) {
            throw new RuntimeException("Flight not found with id: " + id);
        }

        Flight existingFlight = existingFlightOpt.get();

        // Update basic fields
        existingFlight.setFlightNumber(flightData.getFlightNumber());
        existingFlight.setOriginAirport(flightData.getOriginAirport());
        existingFlight.setDestinationAirport(flightData.getDestinationAirport());
        existingFlight.setAirplane(flightData.getAirplane());
        existingFlight.setDepartureDateTime(flightData.getDepartureDateTime());
        existingFlight.setArrivalDateTime(flightData.getArrivalDateTime());
        existingFlight.setFirstClassPrice(flightData.getFirstClassPrice());
        existingFlight.setBusinessClassPrice(flightData.getBusinessClassPrice());
        existingFlight.setEconomyClassPrice(flightData.getEconomyClassPrice());
        existingFlight.setStatus(flightData.getStatus());

        return flightRepository.save(existingFlight);
    }
}
