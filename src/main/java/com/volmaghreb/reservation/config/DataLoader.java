package com.volmaghreb.reservation.config;

import com.volmaghreb.reservation.entities.User;
import com.volmaghreb.reservation.enums.Role;
import com.volmaghreb.reservation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Créer l'utilisateur admin s'il n'existe pas
        if (userRepository.findByEmail("admin@volmaghreb.com").isEmpty()) {
            User adminUser = new User();
            adminUser.setEmail("admin@volmaghreb.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setFirstname("Admin");
            adminUser.setLastname("User");
            adminUser.setRole(Role.ROLE_ADMIN);
            adminUser.setEnabled(true);
            adminUser.setAccountNonLocked(true);
            adminUser.setAccountNonExpired(true);
            adminUser.setCredentialsNonExpired(true);
            
            userRepository.save(adminUser);
            System.out.println("Admin user created successfully!");
        }
    }
} 