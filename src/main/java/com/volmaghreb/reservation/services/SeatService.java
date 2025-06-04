package com.volmaghreb.reservation.services;

import com.volmaghreb.reservation.entities.Flight;
import com.volmaghreb.reservation.entities.Seat;
import com.volmaghreb.reservation.enums.SeatClass;
import org.springframework.stereotype.Service;

public interface SeatService {
    Seat createSeat(SeatClass seatClass, int seatNumber, Flight flight);
}
