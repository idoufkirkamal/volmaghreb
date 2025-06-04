package com.volmaghreb.reservation.dtos;

import com.volmaghreb.reservation.enums.Role;
import com.volmaghreb.reservation.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class UserProfileDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String nationality;
    private Sex sex;
    private String address;
    private Role role;
}
