package com.volmaghreb.reservation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.volmaghreb.reservation.enums.PaymentMethod;
import com.volmaghreb.reservation.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    @Column(nullable = false)
    private Float totalAmount;

    private String transactionID;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @OneToOne(mappedBy = "payment")
    @JsonIgnore
    private Reservation reservation;
}
