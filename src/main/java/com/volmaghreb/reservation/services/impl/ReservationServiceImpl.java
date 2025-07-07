package com.volmaghreb.reservation.services.impl;

import com.volmaghreb.reservation.dtos.ReservationDto;
import com.volmaghreb.reservation.dtos.ReservationRequest;
import com.volmaghreb.reservation.dtos.TravelerInfo;
import com.volmaghreb.reservation.entities.*;
import com.volmaghreb.reservation.enums.PaymentMethod;
import com.volmaghreb.reservation.enums.PaymentStatus;
import com.volmaghreb.reservation.enums.ReservationStatus;
import com.volmaghreb.reservation.enums.SeatClass;
import com.volmaghreb.reservation.mappers.ReservationMapper;
import com.volmaghreb.reservation.repositories.*;
import com.volmaghreb.reservation.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

        // Determine travel class and get appropriate seats
        String travelClass = reservationRequest.getTravelClass();
        if (travelClass == null || travelClass.isEmpty()) {
            travelClass = "ECONOMY"; // Default to economy class
        }

        SeatClass seatClassEnum = getSeatClassEnum(travelClass);
        List<Seat> availableSeats = seatRepository.findByFlightAndSeatClassAndIsAvailable(
                flight, seatClassEnum, true);
        
        if (availableSeats.size() < numberOfTravelers) {
            throw new RuntimeException("Not enough available seats in " + travelClass + " class for this flight.");
        }

        // Calculate price based on travel class
        BigDecimal pricePerSeat = getPriceByTravelClass(flight, travelClass);
        
        Payment payment = new Payment();
        payment.setTotalAmount(pricePerSeat.floatValue() * numberOfTravelers);
        payment.setTransactionDate(LocalDate.now());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setMethod(PaymentMethod.CREDIT_CARD); // Set default payment method
        payment = paymentRepository.save(payment);
        
        List<Reservation> reservations = new ArrayList<>();
        for (int i = 0; i < numberOfTravelers; i++) {
            TravelerInfo travelerInfo = travelers.get(i);
            Seat seat = availableSeats.get(i);

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
            
            // Validate traveler data
            if (traveler.getFirstname() == null || traveler.getFirstname().isEmpty()) {
                throw new RuntimeException("Traveler first name is required");
            }
            if (traveler.getLastname() == null || traveler.getLastname().isEmpty()) {
                throw new RuntimeException("Traveler last name is required");
            }
            if (traveler.getDateOfBirth() == null) {
                throw new RuntimeException("Traveler date of birth is required");
            }
            if (traveler.getNationality() == null || traveler.getNationality().isEmpty()) {
                throw new RuntimeException("Traveler nationality is required");
            }
            if (traveler.getPassportNumber() == null || traveler.getPassportNumber().isEmpty()) {
                throw new RuntimeException("Traveler passport number is required");
            }
            if (traveler.getPassportIssuingCountry() == null || traveler.getPassportIssuingCountry().isEmpty()) {
                throw new RuntimeException("Traveler passport issuing country is required");
            }
            
            traveler = travelerRepository.save(traveler);

            Reservation reservation = new Reservation();
            reservation.setReservationNumber(UUID.randomUUID().toString());
            reservation.setStatus(ReservationStatus.CONFIRMED);
            reservation.setReservationTime(LocalDateTime.now());
            reservation.setReservationDateTime(LocalDateTime.now());
            reservation.setSeatClass(travelClass);
            reservation.setSeatNumber(seat.getSeatNumber());
            reservation.setTotalPrice(pricePerSeat.doubleValue());
            reservation.setUser(user);
            reservation.setFlight(flight);
            reservation.setTraveler(traveler);
            reservation.setSeat(seat);
            reservation.setPayment(payment);

            // Validate that all required fields are set
            if (reservation.getReservationNumber() == null || reservation.getReservationNumber().isEmpty()) {
                throw new RuntimeException("Reservation number is required");
            }
            if (reservation.getStatus() == null) {
                throw new RuntimeException("Reservation status is required");
            }
            if (reservation.getReservationTime() == null) {
                throw new RuntimeException("Reservation time is required");
            }
            if (reservation.getReservationDateTime() == null) {
                throw new RuntimeException("Reservation date time is required");
            }
            if (reservation.getSeatNumber() == null || reservation.getSeatNumber().isEmpty()) {
                throw new RuntimeException("Seat number is required");
            }
            if (reservation.getTotalPrice() == null) {
                throw new RuntimeException("Total price is required");
            }

            // Save the reservation first
            reservation = reservationRepository.save(reservation);
            reservations.add(reservation);
            
            // Now update the seat availability and save
            seat.setAvailable(false);
            seat.setReservation(reservation);
            seatRepository.save(seat);
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

    private SeatClass getSeatClassEnum(String travelClass) {
        switch (travelClass.toUpperCase()) {
            case "FIRST":
            case "FIRST_CLASS":
                return SeatClass.FIRST_CLASS;
            case "BUSINESS":
            case "BUSINESS_CLASS":
                return SeatClass.BUSINESS_CLASS;
            case "ECONOMY":
            case "ECONOMY_CLASS":
            default:
                return SeatClass.ECONOMY_CLASS;
        }
    }

    private BigDecimal getPriceByTravelClass(Flight flight, String travelClass) {
        switch (travelClass.toUpperCase()) {
            case "FIRST":
            case "FIRST_CLASS":
                return flight.getFirstClassPrice();
            case "BUSINESS":
            case "BUSINESS_CLASS":
                return flight.getBusinessClassPrice();
            case "ECONOMY":
            case "ECONOMY_CLASS":
            default:
                return flight.getEconomyClassPrice();
        }
    }
}
