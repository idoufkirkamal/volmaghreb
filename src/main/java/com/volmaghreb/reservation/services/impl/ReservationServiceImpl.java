package com.volmaghreb.reservation.services.impl;

import com.volmaghreb.reservation.dtos.ReservationDto;
import com.volmaghreb.reservation.dtos.ReservationRequest;
import com.volmaghreb.reservation.entities.*;
import com.volmaghreb.reservation.enums.PaymentStatus;
import com.volmaghreb.reservation.enums.ReservationStatus;
import com.volmaghreb.reservation.mappers.ReservationMapper;
import com.volmaghreb.reservation.repositories.*;
import com.volmaghreb.reservation.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final TravelerRepository travelerRepository;
    private final SeatRepository seatRepository;
    private final PaymentRepository paymentRepository;
    private final ReservationMapper reservationMapper;


    @Override
    public Reservation createReservation(ReservationRequest reservationRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));

        Flight flight = flightRepository.findById(reservationRequest.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        List<Seat> availableSeats = seatRepository.findByFlightAndIsAvailable(flight, true);
        if (availableSeats.isEmpty()) {
            throw new RuntimeException("No available seats on this flight.");
        }
        Seat seat = availableSeats.get(0);
        seat.setAvailable(false);
        seatRepository.save(seat);

        Traveler traveler = new Traveler();
        traveler.setName(reservationRequest.getPassengerName());
        traveler.setEmail(reservationRequest.getPassengerEmail());
        traveler.setPhone(reservationRequest.getPassengerPhone());
        traveler = travelerRepository.save(traveler);

        Payment payment = new Payment();
        payment.setTotalAmount(flight.getEconomyClassPrice().floatValue());
        payment.setTransactionDate(LocalDate.now());
        payment.setStatus(PaymentStatus.PENDING);
        payment = paymentRepository.save(payment);


        Reservation reservation = new Reservation();
        reservation.setReservationNumber(UUID.randomUUID().toString());
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setUser(user);
        reservation.setFlight(flight);
        reservation.setTraveler(traveler);
        reservation.setSeat(seat);
        reservation.setPayment(payment);

        return reservationRepository.save(reservation);
    }

    @Override
    public Page<ReservationDto> getReservations(int page, int size) {
        Page<Reservation> reservationPage = reservationRepository.findAll(PageRequest.of(page, size));
        return reservationPage.map(reservationMapper::toDto);
    }

    @Override
    public List<ReservationDto> getReservationsForCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        List<Reservation> reservations = reservationRepository.findByUser(user);
        return reservations.stream().map(reservationMapper::toDto).toList();
    }
}
