package com.volmaghreb.reservation.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ReservationRequest {
    private Long flightId;
    private List<TravelerInfo> travelers;
    private String travelClass; // Added travel class field
}
