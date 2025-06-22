package com.volmaghreb.reservation.dtos;

import com.volmaghreb.reservation.enums.ReservationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDto {
    private Long id;
    private String reservationNumber;
    private ReservationStatus status;
    private LocalDateTime reservationDate;
    private Long userId;
    private Long flightId;
}
