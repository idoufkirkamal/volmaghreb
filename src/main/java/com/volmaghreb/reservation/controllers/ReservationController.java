package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.dtos.ReservationRequest;
import com.volmaghreb.reservation.entities.Reservation;
import com.volmaghreb.reservation.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<List<Reservation>> createReservation(@RequestBody ReservationRequest reservationRequest) {
        List<Reservation> reservations = reservationService.createReservation(reservationRequest);
        return new ResponseEntity<>(reservations, HttpStatus.CREATED);
    }
}
