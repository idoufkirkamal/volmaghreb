package com.volmaghreb.reservation.dtos;

import com.volmaghreb.reservation.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminReservationDto {
    private Long id;
    private String reservationNumber;
    private ReservationStatus status;
    private LocalDateTime reservationTime;
    private LocalDateTime reservationDateTime;
    private String seatClass;
    private String seatNumber;
    private Double totalPrice;
    
    // User information
    private Long userId;
    private String userFirstname;
    private String userLastname;
    private String userEmail;
    
    // Flight information
    private FlightDto flight;
    
    // Traveler information
    private Long travelerId;
    private String travelerName;
    private String travelerFirstname;
    private String travelerLastname;
    
    // Payment information
    private Long paymentId;
    private String paymentStatus;
    private Float paymentAmount;
}
