package com.volmaghreb.reservation.entities;

import com.volmaghreb.reservation.enums.Role;
import com.volmaghreb.reservation.enums.Sex;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;

    @Column(unique = true, nullable = false)
    private String email;
    private String phone;
    private String password;
    private LocalDate dateOfBirth;
    private String nationality;
    private Sex sex;
    private String address;
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
}
