package com.volmaghreb.reservation.services;

import com.volmaghreb.reservation.dtos.PasswordUpdateDTO;
import com.volmaghreb.reservation.dtos.UserProfileDTO;
import org.springframework.validation.BindingResult;

public interface UserService {
    UserProfileDTO findByEmail(String email);
    void updateUser(String email, UserProfileDTO user);
    boolean updatePassword(String email, PasswordUpdateDTO dto, BindingResult result);
}
