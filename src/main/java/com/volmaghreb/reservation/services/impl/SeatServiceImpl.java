package com.volmaghreb.reservation.services.impl;

import com.volmaghreb.reservation.entities.Flight;
import com.volmaghreb.reservation.entities.Seat;
import com.volmaghreb.reservation.enums.SeatClass;
import com.volmaghreb.reservation.repositories.SeatRepository;
import com.volmaghreb.reservation.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public Seat createSeat(SeatClass seatClass, int seatNumber, Flight flight) {
        Seat seat = new Seat();
        seat.setSeatClass(seatClass);
        seat.setSeatNumber("" + seatClass.name().charAt(0)+seatNumber);
        seat.setAvailable(true);
        seat.setFlight(flight);

        return seat;
    }
}
