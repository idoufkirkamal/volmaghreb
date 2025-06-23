package com.volmaghreb.reservation.services.impl;

import com.volmaghreb.reservation.dtos.ReservationDto;
import com.volmaghreb.reservation.dtos.ReservationRequest;
import com.volmaghreb.reservation.dtos.TravelerInfo;
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
import java.util.ArrayList;
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
    public List<Reservation> createReservation(ReservationRequest reservationRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));

        Flight flight = flightRepository.findById(reservationRequest.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        List<TravelerInfo> travelers = reservationRequest.getTravelers();
        int numberOfTravelers = travelers.size();

        List<Seat> availableSeats = seatRepository.findByFlightAndIsAvailable(flight, true);
        if (availableSeats.size() < numberOfTravelers) {
            throw new RuntimeException("Not enough available seats on this flight.");
        }

        Payment payment = new Payment();
        payment.setTotalAmount(flight.getEconomyClassPrice().floatValue() * numberOfTravelers);
        payment.setTransactionDate(LocalDate.now());
        payment.setStatus(PaymentStatus.PENDING);
        payment = paymentRepository.save(payment);

        List<Reservation> reservations = new ArrayList<>();
        for (int i = 0; i < numberOfTravelers; i++) {
            TravelerInfo travelerInfo = travelers.get(i);
            Seat seat = availableSeats.get(i);
            seat.setAvailable(false);
            seatRepository.save(seat);

            Traveler traveler = new Traveler();
            traveler.setFirstname(travelerInfo.getFirstName());
            traveler.setLastname(travelerInfo.getLastName());
            traveler.setName(travelerInfo.getFirstName() + " " + travelerInfo.getLastName());
            traveler.setDateOfBirth(travelerInfo.getDateOfBirth());
            traveler.setNationality(travelerInfo.getNationality());
            traveler.setPassportNumber(travelerInfo.getPassportNumber());
            traveler.setPassportIssuingCountry(travelerInfo.getPassportIssuingCountry());
            if (travelerInfo.getPassportExpiry() != null) {
                traveler.setPassportExpirationDate(travelerInfo.getPassportExpiry().atStartOfDay());
            }
            traveler = travelerRepository.save(traveler);

            Reservation reservation = new Reservation();
            reservation.setReservationNumber(UUID.randomUUID().toString());
            reservation.setStatus(ReservationStatus.CONFIRMED);
            reservation.setReservationTime(LocalDateTime.now());
            reservation.setUser(user);
            reservation.setFlight(flight);
            reservation.setTraveler(traveler);
            reservation.setSeat(seat);
            reservation.setPayment(payment);

            reservations.add(reservationRepository.save(reservation));
        }

        return reservations;
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
