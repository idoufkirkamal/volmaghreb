package com.volmaghreb.reservation.services;

import com.volmaghreb.reservation.entities.Flight;
import com.volmaghreb.reservation.entities.Seat;
import com.volmaghreb.reservation.enums.SeatClass;

import java.util.List;

public interface SeatService {
    Seat createSeat(SeatClass seatClass, int seatNumber, Flight flight);
    void createSeatsForFlight(Flight flight);
    long countSeatsByFlight(Flight flight);
    List<Seat> getSeatsByFlight(Flight flight);
}
