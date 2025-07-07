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
    @Column(nullable = false)
    private ReservationStatus status;

    @Column(name = "reservation_time", nullable = false)
    private LocalDateTime reservationTime;

    @Column(name = "reservation_date_time", nullable = false)
    private LocalDateTime reservationDateTime;

    @Column(name = "seat_class")
    private String seatClass;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("user-reservations")
    private User user;

    @OneToOne
    @JoinColumn(name = "seat_id", nullable = false)
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
