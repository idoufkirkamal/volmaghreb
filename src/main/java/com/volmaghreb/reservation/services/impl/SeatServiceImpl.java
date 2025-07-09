package com.volmaghreb.reservation.services.impl;

import com.volmaghreb.reservation.entities.Airplane;
import com.volmaghreb.reservation.entities.Flight;
import com.volmaghreb.reservation.entities.Seat;
import com.volmaghreb.reservation.enums.SeatClass;
import com.volmaghreb.reservation.repositories.AirplaneRepository;
import com.volmaghreb.reservation.repositories.SeatRepository;
import com.volmaghreb.reservation.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;
    
    @Autowired
    private AirplaneRepository airplaneRepository;

    @Override
    public Seat createSeat(SeatClass seatClass, int seatNumber, Flight flight) {
        Seat seat = new Seat();
        seat.setSeatClass(seatClass);
        seat.setSeatNumber("" + seatClass.name().charAt(0) + seatNumber);
        seat.setAvailable(true);
        seat.setFlight(flight);

        return seatRepository.save(seat);
    }

    @Override
    @Transactional
    public void createSeatsForFlight(Flight flight) {
        // Check if seats already exist for this flight
        if (seatRepository.existsByFlight(flight)) {
            return;
        }
        
        // Validate airplane exists
        if (flight.getAirplane() == null) {
            throw new RuntimeException("Cannot create seats: Flight has no airplane assigned");
        }
        
        // Force fetch the airplane from the database to ensure we have all the data
        Airplane airplane = airplaneRepository.findById(flight.getAirplane().getId())
                .orElseThrow(() -> new RuntimeException("Airplane not found with ID: " + flight.getAirplane().getId()));
        
        List<Seat> seats = new ArrayList<>();

        // Create First Class seats
        int firstClassCapacity = airplane.getFirstClassCapacity();
        for (int i = 1; i <= firstClassCapacity; i++) {
            Seat seat = new Seat();
            seat.setSeatClass(SeatClass.FIRST_CLASS);
            seat.setSeatNumber("F" + i);
            seat.setAvailable(true);
            seat.setFlight(flight);
            seats.add(seat);
        }

        // Create Business Class seats
        int businessClassCapacity = airplane.getBusinessClassCapacity();
        for (int i = 1; i <= businessClassCapacity; i++) {
            Seat seat = new Seat();
            seat.setSeatClass(SeatClass.BUSINESS_CLASS);
            seat.setSeatNumber("B" + i);
            seat.setAvailable(true);
            seat.setFlight(flight);
            seats.add(seat);
        }

        // Create Economy Class seats
        int economyClassCapacity = airplane.getEconomyClassCapacity();
        for (int i = 1; i <= economyClassCapacity; i++) {
            Seat seat = new Seat();
            seat.setSeatClass(SeatClass.ECONOMY_CLASS);
            seat.setSeatNumber("E" + i);
            seat.setAvailable(true);
            seat.setFlight(flight);
            seats.add(seat);
        }

        // Validate that we have some seats to create
        if (seats.isEmpty()) {
            return;
        }

        // Save all seats in batch
        seatRepository.saveAll(seats);
    }

    @Override
    public long countSeatsByFlight(Flight flight) {
        return seatRepository.countByFlight(flight);
    }

    @Override
    public List<Seat> getSeatsByFlight(Flight flight) {
        return seatRepository.findByFlight(flight);
    }
}
