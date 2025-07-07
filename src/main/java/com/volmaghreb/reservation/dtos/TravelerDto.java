package com.volmaghreb.reservation.dtos;

import com.volmaghreb.reservation.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelerDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private String nationality;
    private Sex sex;
    private String address;
    private String passportNumber;
    private String passportIssuingCountry;
    
    public String getFullName() {
        if (firstname != null && lastname != null) {
            return firstname + " " + lastname;
        } else if (name != null) {
            return name;
        }
        return "N/A";
    }
}
