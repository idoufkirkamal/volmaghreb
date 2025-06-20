package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.dtos.PasswordUpdateDTO;
import com.volmaghreb.reservation.dtos.UserProfileDTO;
import com.volmaghreb.reservation.enums.Role;
import com.volmaghreb.reservation.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping({"/users/profile", "/admin/profile"})
    public String getUser(Model model, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : "abdallahradfi@gmail.com";

        UserProfileDTO profile = userService.findByEmail(email);

        model.addAttribute("user", profile);
        model.addAttribute("passwordUpdate", new PasswordUpdateDTO());
        return profile.getRole().equals(Role.USER) ? "user/profile" : "admin/profile";
    }

    @PostMapping("/users")
    public String updateUser(@ModelAttribute("user") UserProfileDTO user, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : "abdallahradfi@gmail.com";
        userService.updateUser(email, user);
        return "redirect:/users/profile";
    }

    @PostMapping("/users/update-password")
    public String updatePassword(@Valid @ModelAttribute("passwordUpdate") PasswordUpdateDTO dto, BindingResult result, Model model, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : "abdallahradfi@gmail.com";

        if (result.hasErrors() || !userService.updatePassword(email, dto, result)) {
            model.addAttribute("passwordUpdate", new PasswordUpdateDTO());
            model.addAttribute("user", userService.findByEmail(email));
            return "user/profile";
        }

        return "redirect:/users/profile";
    }
}
