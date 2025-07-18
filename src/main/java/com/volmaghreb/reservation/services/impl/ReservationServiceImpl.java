package com.volmaghreb.reservation.services.impl;

import com.volmaghreb.reservation.dtos.AdminReservationDto;
import com.volmaghreb.reservation.dtos.ReservationDto;
import com.volmaghreb.reservation.dtos.ReservationRequest;
import com.volmaghreb.reservation.dtos.TravelerInfo;
import com.volmaghreb.reservation.entities.*;
import com.volmaghreb.reservation.enums.PaymentMethod;
import com.volmaghreb.reservation.enums.PaymentStatus;
import com.volmaghreb.reservation.enums.ReservationStatus;
import com.volmaghreb.reservation.enums.SeatClass;
import com.volmaghreb.reservation.mappers.AdminReservationMapper;
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
    private final AdminReservationMapper adminReservationMapper;


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
        
        List<Reservation> reservations = new ArrayList<>();
        
        for (int i = 0; i < numberOfTravelers; i++) {
            TravelerInfo travelerInfo = travelers.get(i);
            
            // Get a fresh available seat for each traveler to avoid stale data
            List<Seat> currentAvailableSeats = seatRepository.findByFlightAndSeatClassAndIsAvailable(
                    flight, seatClassEnum, true);
            
            if (currentAvailableSeats.isEmpty()) {
                throw new RuntimeException("No more available seats in " + travelClass + " class for this flight.");
            }
            
            // Take the first available seat
            Seat seat = currentAvailableSeats.get(0);
            
            // Immediately mark the seat as unavailable to prevent race conditions
            seat.setAvailable(false);
            seat = seatRepository.save(seat);

            // Create a separate payment for each reservation
            Payment payment = new Payment();
            payment.setTotalAmount(pricePerSeat.floatValue());
            payment.setTransactionDate(LocalDate.now());
            payment.setStatus(PaymentStatus.PENDING);
            payment.setMethod(PaymentMethod.CREDIT_CARD);
            payment = paymentRepository.save(payment);

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
            reservation.setReservationNumber(generateReservationNumber());
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

            // Save the reservation
            reservation = reservationRepository.save(reservation);
            reservations.add(reservation);
        }

        return reservations;
    }

    @Override
    public Page<ReservationDto> getReservations(int page, int size) {
        Page<Reservation> reservationPage = reservationRepository.findAll(PageRequest.of(page, size));
        return reservationPage.map(reservationMapper::toDto);
    }

    @Override
    public Page<AdminReservationDto> getAdminReservations(int page, int size) {
        Page<Reservation> reservationPage = reservationRepository.findAll(PageRequest.of(page, size));
        return reservationPage.map(adminReservationMapper::toDto);
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

    @Override
    public ReservationDto getReservationById(Long id) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        
        // Security check: only allow users to view their own reservations
        if (!reservation.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }
        
        return reservationMapper.toDto(reservation);
    }

    @Override
    public AdminReservationDto getAdminReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        return adminReservationMapper.toDto(reservation);
    }

    @Override
    public boolean cancelReservation(Long reservationId) {
        System.out.println("Service: Attempting to cancel reservation with ID: " + reservationId);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Service: Current user: " + username);
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        
        System.out.println("Service: Found reservation with status: " + reservation.getStatus());
        
        // Security check: only allow users to cancel their own reservations
        if (!reservation.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied: You can only cancel your own reservations");
        }
        
        // Check if reservation can be cancelled (not already cancelled or completed)
        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new RuntimeException("Reservation is already cancelled");
        }
        
        if (reservation.getStatus() == ReservationStatus.COMPLETED) {
            throw new RuntimeException("Cannot cancel a completed reservation");
        }
        
        // Update reservation status to cancelled
        System.out.println("Service: Setting reservation status to CANCELLED");
        reservation.setStatus(ReservationStatus.CANCELLED);
        Reservation savedReservation = reservationRepository.save(reservation);
        System.out.println("Service: Saved reservation with new status: " + savedReservation.getStatus());
        
        // Note: We don't update payment status as the payment was already processed successfully
        // The payment remains as SUCCESS/COMPLETED to maintain financial record integrity
        
        System.out.println("Service: Cancellation completed successfully");
        return true;
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

    private String generateReservationNumber() {
        // Get the current count of reservations to generate a sequential number
        long count = reservationRepository.count();
        // Format as BK followed by 4-digit number (e.g., BK0001, BK0002, etc.)
        return String.format("BK%04d", count + 1);
    }
}
