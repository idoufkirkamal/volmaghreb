package com.volmaghreb.reservation.services;

import com.volmaghreb.reservation.entities.Seat;
import com.volmaghreb.reservation.enums.SeatClass;
import org.springframework.stereotype.Service;
import org.volmaghreb.reservation.entities.Flight;

public interface SeatService {
    Seat createSeat(SeatClass seatClass, int seatNumber, Flight flight);
}
