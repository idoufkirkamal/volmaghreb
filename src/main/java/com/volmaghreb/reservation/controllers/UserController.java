package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.dtos.PasswordUpdateDTO;
import com.volmaghreb.reservation.dtos.UserProfileDTO;
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

    @GetMapping("/admin/users")
    public String getUsers(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("page", userService.getAll(page, 5));
        return "admin/admin-users";
    }

    @GetMapping("/users/profile")
    public String getUser(Model model, Authentication authentication) {
        String email = authentication.getName();

        UserProfileDTO profile = userService.findByEmail(email);

        model.addAttribute("user", profile);
        model.addAttribute("passwordUpdate", new PasswordUpdateDTO());
        return "user/profile";
    }

    @PostMapping("/users")
    public String updateUser(@ModelAttribute("user") UserProfileDTO user, Authentication authentication) {
        String email = authentication.getName();
        userService.updateUser(email, user);
        return "redirect:/users/profile";
    }

    @PostMapping("/users/update-password")
    public String updatePassword(@Valid @ModelAttribute("passwordUpdate") PasswordUpdateDTO dto, BindingResult result, Model model, Authentication authentication) {
        String email = authentication.getName();

        if (result.hasErrors() || !userService.updatePassword(email, dto, result)) {
            model.addAttribute("passwordUpdate", new PasswordUpdateDTO());
            model.addAttribute("user", userService.findByEmail(email));
            return "user/profile";
        }

        return "redirect:/users/profile";
    }
}
