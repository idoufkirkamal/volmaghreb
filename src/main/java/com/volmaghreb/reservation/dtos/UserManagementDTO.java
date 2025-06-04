package com.volmaghreb.reservation.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserManagementDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String nationality;
    private Long numberOfReservations;
}