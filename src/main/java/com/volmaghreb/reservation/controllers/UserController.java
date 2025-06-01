package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.dtos.UserProfileDTO;
import com.volmaghreb.reservation.entities.User;
import com.volmaghreb.reservation.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/users/profile")
    public String getUser(Model model, Authentication authentication) {
        String email = authentication.getName();

        User user = userService.findByEmail(email);

        UserProfileDTO profile = new UserProfileDTO(
            user.getFirstname(),
            user.getLastname(),
            user.getEmail(),
            user.getPhone(),
            user.getDateOfBirth(),
            user.getNationality(),
            user.getSex(),
            user.getAddress()
        );
        model.addAttribute("user", profile);
        return "user/profile";
    }

    @PostMapping("/users")
    public String updateUser(@ModelAttribute("user") UserProfileDTO user, Authentication authentication) {
        String email = authentication.getName();
        userService.updateUser(email, user);
        return "redirect:/users/profile";
    }
}
