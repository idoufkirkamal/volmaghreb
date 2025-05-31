package com.volmaghreb.reservation.services.impl;

import com.volmaghreb.reservation.entities.User;
import com.volmaghreb.reservation.repositories.UserRepository;
import com.volmaghreb.reservation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
