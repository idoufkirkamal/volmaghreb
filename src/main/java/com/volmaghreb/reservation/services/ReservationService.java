package com.volmaghreb.reservation.services;

import com.volmaghreb.reservation.dtos.ReservationDto;
import com.volmaghreb.reservation.dtos.ReservationRequest;
import com.volmaghreb.reservation.entities.Reservation;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReservationService {
    Reservation createReservation(ReservationRequest reservationRequest);
    Page<ReservationDto> getReservations(int page, int size);
    List<ReservationDto> getReservationsForCurrentUser();
}
