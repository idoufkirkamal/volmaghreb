package com.volmaghreb.reservation.dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TravelerInfo {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String nationality;
    private String passportNumber;
    private String passportIssuingCountry;
    private LocalDate passportExpiry;
}
