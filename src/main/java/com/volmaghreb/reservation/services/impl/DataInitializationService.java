package com.volmaghreb.reservation.services.impl;

import com.volmaghreb.reservation.entities.User;
import com.volmaghreb.reservation.enums.Role;
import com.volmaghreb.reservation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DataInitializationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeData() {
        // Créer un utilisateur admin si aucun n'existe
        if (userRepository.findByEmail("admin@volmaghreb.com").isEmpty()) {
            User adminUser = new User();
            adminUser.setEmail("admin@volmaghreb.com");
            adminUser.setPassword(passwordEncoder.encode("admin123")); // Mot de passe: admin123
            adminUser.setFirstname("Admin");
            adminUser.setLastname("User");
            adminUser.setRole(Role.ADMIN);
            
            userRepository.save(adminUser);
            System.out.println("Admin user created successfully!");
        }
    }
} 