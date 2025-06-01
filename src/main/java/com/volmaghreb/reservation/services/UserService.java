package com.volmaghreb.reservation.services;

import com.volmaghreb.reservation.dtos.UserProfileDTO;
import com.volmaghreb.reservation.entities.User;

public interface UserService {
    User findByEmail(String email);
    void updateUser(String email, UserProfileDTO user);
}
