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
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private String nationality;
    private Sex sex;
    private String address;
    private String passportNumber;
    private String passportIssuingCountry;
    private LocalDateTime passportExpirationDate;

    @OneToOne(mappedBy = "traveler", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Reservation reservation;
}
