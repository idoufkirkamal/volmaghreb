package com.volmaghreb.reservation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.volmaghreb.reservation.enums.Sex;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "traveler")
@Data
public class Traveler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    @Column(nullable = false)
    private String nationality;
    private Sex sex;
    private String address;
    @Column(nullable = false)
    private String passportNumber;
    @Column(nullable = false)
    private String passportIssuingCountry;
    private LocalDateTime passportExpirationDate;

    @OneToOne(mappedBy = "traveler", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Reservation reservation;
}
