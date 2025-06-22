package com.volmaghreb.reservation.dtos;

import lombok.Data;

@Data
public class ReservationRequest {
    private Long flightId;
    private String passengerName;
    private String passengerEmail;
    private String passengerPhone;
}
