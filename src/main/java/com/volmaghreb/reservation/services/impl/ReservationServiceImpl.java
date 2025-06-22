package com.volmaghreb.reservation.services.impl;

import com.volmaghreb.reservation.dtos.ReservationDto;
import com.volmaghreb.reservation.entities.Flight;
import com.volmaghreb.reservation.entities.Reservation;
import com.volmaghreb.reservation.entities.User;
import com.volmaghreb.reservation.repositories.FlightRepository;
import com.volmaghreb.reservation.repositories.ReservationRepository;
import com.volmaghreb.reservation.repositories.UserRepository;
import com.volmaghreb.reservation.services.ReservationService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;

    @Override
    public ReservationDto createReservation(ReservationDto reservationDto) {
        User user = userRepository.findById(reservationDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Flight flight = flightRepository.findById(reservationDto.getFlightId()).orElseThrow(() -> new RuntimeException("Flight not found"));

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setFlight(flight);
        reservation.setReservationDate(reservationDto.getReservationDate());
        reservation.setStatus(reservationDto.getStatus());
        reservation.setReservationNumber(reservationDto.getReservationNumber());

        Reservation savedReservation = reservationRepository.save(reservation);
        return toDto(savedReservation);
    }

    @Override
    public ReservationDto getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
        return toDto(reservation);
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        return reservationRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public ReservationDto updateReservation(Long id, ReservationDto reservationDto) {
        Reservation existingReservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
        existingReservation.setStatus(reservationDto.getStatus());
        Reservation updatedReservation = reservationRepository.save(existingReservation);
        return toDto(updatedReservation);
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    private ReservationDto toDto(Reservation reservation) {
        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setReservationNumber(reservation.getReservationNumber());
        dto.setStatus(reservation.getStatus());
        dto.setReservationDate(reservation.getReservationDate());
        dto.setUserId(reservation.getUser().getId());
        dto.setFlightId(reservation.getFlight().getId());
        return dto;
    }
}
