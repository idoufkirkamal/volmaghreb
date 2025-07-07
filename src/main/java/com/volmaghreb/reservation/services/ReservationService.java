package com.volmaghreb.reservation.services;

import com.volmaghreb.reservation.dtos.AdminReservationDto;
import com.volmaghreb.reservation.dtos.ReservationDto;
import com.volmaghreb.reservation.dtos.ReservationRequest;
import com.volmaghreb.reservation.entities.Reservation;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReservationService {
    List<Reservation> createReservation(ReservationRequest reservationRequest);
    Page<ReservationDto> getReservations(int page, int size);
    Page<AdminReservationDto> getAdminReservations(int page, int size);
    List<ReservationDto> getReservationsForCurrentUser();
    ReservationDto getReservationById(Long id);
    AdminReservationDto getAdminReservationById(Long id);
    boolean cancelReservation(Long reservationId);
}
