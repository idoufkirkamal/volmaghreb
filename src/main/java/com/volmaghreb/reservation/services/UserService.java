package com.volmaghreb.reservation.services;

import com.volmaghreb.reservation.entities.User;

public interface UserService {
    User findByEmail(String email);
}
