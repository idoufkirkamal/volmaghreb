package com.volmaghreb.reservation.entities;

import com.volmaghreb.reservation.enums.PaymentMethod;
import com.volmaghreb.reservation.enums.PaymentStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    private Float totalAmount;

    private String transactionID;

    private LocalDate transactionDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @OneToOne(mappedBy = "payment")
    private Reservation reservation;
}
