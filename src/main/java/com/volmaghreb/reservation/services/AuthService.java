package com.volmaghreb.reservation.services;

import com.volmaghreb.reservation.dtos.LoginDTO;
import org.springframework.security.core.Authentication;

public interface AuthService {
    Authentication authenticate(LoginDTO loginDTO);
} 