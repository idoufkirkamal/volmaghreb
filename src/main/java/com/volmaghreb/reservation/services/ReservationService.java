package com.volmaghreb.reservation.services;

import com.volmaghreb.reservation.dtos.ReservationDto;

import java.util.List;

public interface ReservationService {
    ReservationDto createReservation(ReservationDto reservationDto);
    ReservationDto getReservationById(Long id);
    List<ReservationDto> getAllReservations();
    ReservationDto updateReservation(Long id, ReservationDto reservationDto);
    void deleteReservation(Long id);
}
