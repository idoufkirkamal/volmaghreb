package com.volmaghreb.reservation.services.impl;

import com.volmaghreb.reservation.dtos.FlightDto;
import com.volmaghreb.reservation.entities.Airplane;
import com.volmaghreb.reservation.entities.Flight;
import com.volmaghreb.reservation.entities.Seat;
import com.volmaghreb.reservation.enums.FlightStatus;
import com.volmaghreb.reservation.enums.SeatClass;
import com.volmaghreb.reservation.mappers.FlightMapper;
import com.volmaghreb.reservation.repositories.AirplaneRepository;
import com.volmaghreb.reservation.repositories.FlightRepository;
import com.volmaghreb.reservation.services.FlightService;
import com.volmaghreb.reservation.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {
    
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    AirplaneRepository airplaneRepository;
    
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
    public Optional<FlightDto> getFlightDtoById(Long id) {
        return flightRepository.findById(id).map(flightMapper::toDto);
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
        
        // Validate airplane is assigned
        if (flight.getAirplane() == null) {
            throw new RuntimeException("Flight must have an airplane assigned");
        }
        
        // Save the flight first
        Flight savedFlight = flightRepository.save(flight);

        // Create seats automatically for the flight
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
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        
        // If departure date is provided, create a range for the entire day
        if (departureDate != null) {
            startDate = departureDate.atStartOfDay(); // 00:00:00
            endDate = departureDate.atTime(23, 59, 59); // 23:59:59
        }
        
        // Use the optimized repository query
        return flightRepository.findFlightsBySearchCriteria(originId, destinationId, startDate, endDate);
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
    
    @Override
    public List<Flight> searchFlightsWithAvailability(Long originId, Long destinationId, LocalDate departureDate, String travelClass, Integer requiredSeats) {
        List<Flight> flights = searchFlights(originId, destinationId, departureDate);
        
        if (travelClass == null || requiredSeats == null) {
            return flights;
        }
        
        // Filter flights based on seat availability for the specified class
        return flights.stream()
            .filter(flight -> hasAvailableSeats(flight, travelClass, requiredSeats))
            .collect(Collectors.toList());
    }
    
    private boolean hasAvailableSeats(Flight flight, String travelClass, Integer requiredSeats) {
        if (flight.getAirplane() == null) {
            return false;
        }
        
        int availableSeats = 0;
        
        switch (travelClass.toUpperCase()) {
            case "FIRST_CLASS":
                availableSeats = flight.getAirplane().getFirstClassCapacity() - getBookedSeats(flight, SeatClass.FIRST_CLASS);
                break;
            case "BUSINESS":
            case "BUSINESS_CLASS":
                availableSeats = flight.getAirplane().getBusinessClassCapacity() - getBookedSeats(flight, SeatClass.BUSINESS_CLASS);
                break;
            case "ECONOMY":
            case "ECONOMY_CLASS":
                availableSeats = flight.getAirplane().getEconomyClassCapacity() - getBookedSeats(flight, SeatClass.ECONOMY_CLASS);
                break;
            default:
                return false;
        }
        
        return availableSeats >= requiredSeats;
    }
    
    private int getBookedSeats(Flight flight, SeatClass seatClass) {
        // Count actual booked seats by querying reservations for this flight and seat class
        return (int) flight.getReservations().stream()
            .filter(reservation -> reservation.getSeat() != null && 
                                 reservation.getSeat().getSeatClass() == seatClass)
            .count();
    }
}
