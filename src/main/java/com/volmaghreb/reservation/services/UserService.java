package com.volmaghreb.reservation.services;

import com.volmaghreb.reservation.dtos.PasswordUpdateDTO;
import com.volmaghreb.reservation.dtos.UserManagementDTO;
import com.volmaghreb.reservation.dtos.UserProfileDTO;
import com.volmaghreb.reservation.utilities.PaginatedResponse;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UserService {
    PaginatedResponse<UserManagementDTO> getAllClients(int page, int size);
    UserProfileDTO findByEmail(String email);
    void updateUser(String email, UserProfileDTO user);
    boolean updatePassword(String email, PasswordUpdateDTO dto, BindingResult result);
}
