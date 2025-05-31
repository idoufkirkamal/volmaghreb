package com.volmaghreb.reservation.dtos;

import com.volmaghreb.reservation.entities.Reservation;
import com.volmaghreb.reservation.enums.Role;
import com.volmaghreb.reservation.enums.Sex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
public class UserProfileDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String nationality;
    private Sex sex;
    private String address;
}
