package com.volmaghreb.reservation.enums;

public enum ReservationStatus {
    PENDING,        // The reservation is created but not yet confirmed
    CONFIRMED,      // The reservation is paid or confirmed
    CANCELLED,      // The user or admin cancelled the reservation
    COMPLETED       // The flight has been taken and the reservation is done
}
