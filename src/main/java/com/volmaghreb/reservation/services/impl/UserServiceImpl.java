package com.volmaghreb.reservation.services.impl;

import com.volmaghreb.reservation.dtos.UserProfileDTO;
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

    @Override
    public void updateUser(String email, UserProfileDTO user) {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setPhone(user.getPhone());
        existingUser.setNationality(user.getNationality());
        existingUser.setDateOfBirth(user.getDateOfBirth());
        existingUser.setSex(user.getSex());
        existingUser.setAddress(user.getAddress());

        userRepository.save(existingUser);
    }
}
