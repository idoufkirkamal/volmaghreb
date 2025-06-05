package com.volmaghreb.reservation.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.volmaghreb.reservation.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reservationNumber;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(nullable = false)
    private LocalDateTime reservationTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("user-reservations")
    private User user;

    @OneToOne
    @JoinColumn(name = "seat_id", nullable=false)
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    @JsonBackReference("flight-reservations")
    private Flight flight;

   @OneToOne
   @JoinColumn(name = "traveler_id", nullable = false)
   private Traveler traveler;

   @OneToOne
   @JoinColumn(name = "payment_id", nullable = false)
   private Payment payment;
}
