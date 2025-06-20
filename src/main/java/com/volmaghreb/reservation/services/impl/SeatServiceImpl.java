package com.volmaghreb.reservation.services.impl;

import com.volmaghreb.reservation.entities.Flight;
import com.volmaghreb.reservation.entities.Seat;
import com.volmaghreb.reservation.enums.SeatClass;
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
            System.out.println("Seats already exist for flight: " + flight.getFlightNumber());
            return;
        }

        System.out.println("Creating seats for flight: " + flight.getFlightNumber());
        List<Seat> seats = new ArrayList<>();

        // Create First Class seats
        for (int i = 1; i <= flight.getAirplane().getFirstClassCapacity(); i++) {
            Seat seat = new Seat();
            seat.setSeatClass(SeatClass.FIRST_CLASS);
            seat.setSeatNumber("F" + i);
            seat.setAvailable(true);
            seat.setFlight(flight);
            seats.add(seat);
        }

        // Create Business Class seats
        for (int i = 1; i <= flight.getAirplane().getBusinessClassCapacity(); i++) {
            Seat seat = new Seat();
            seat.setSeatClass(SeatClass.BUSINESS_CLASS);
            seat.setSeatNumber("B" + i);
            seat.setAvailable(true);
            seat.setFlight(flight);
            seats.add(seat);
        }

        // Create Economy Class seats
        for (int i = 1; i <= flight.getAirplane().getEconomyClassCapacity(); i++) {
            Seat seat = new Seat();
            seat.setSeatClass(SeatClass.ECONOMY_CLASS);
            seat.setSeatNumber("E" + i);
            seat.setAvailable(true);
            seat.setFlight(flight);
            seats.add(seat);
        }

        // Save all seats in batch
        seatRepository.saveAll(seats);
        System.out.println("Created " + seats.size() + " seats for flight: " + flight.getFlightNumber());
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
