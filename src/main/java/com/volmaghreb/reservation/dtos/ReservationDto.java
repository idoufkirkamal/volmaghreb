package com.volmaghreb.reservation.dtos;

import com.volmaghreb.reservation.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private String reservationNumber;
    private ReservationStatus status;
    private LocalDate reservationDate;
    private Long userId;
    private FlightDto flight;
    private TravelerDto traveler;
}
