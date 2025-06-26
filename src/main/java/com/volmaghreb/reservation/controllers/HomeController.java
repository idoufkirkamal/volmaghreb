package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.services.AirportService;
import com.volmaghreb.reservation.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private AirportService airportService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin/dashboard";
            } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CLIENT"))) {
                return "redirect:/flights";
            }
        }

        model.addAttribute("pageTitle", "Flight Search - Volmaghreb");
        model.addAttribute("currentPage", "home");
        model.addAttribute("airports", airportService.getAllAirports());

        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            try {
                model.addAttribute("user", userService.findByEmail(authentication.getName()));
            } catch (Exception e) {
                model.addAttribute("user", null);
            }
        } else {
            model.addAttribute("user", null);
        }

        return "flights/index";
    }

    @GetMapping("/about")
    public String about(Model model, Authentication authentication) {
        model.addAttribute("pageTitle", "About Us - Volmaghreb");
        model.addAttribute("currentPage", "about");

        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            try {
                model.addAttribute("user", userService.findByEmail(authentication.getName()));
            } catch (Exception e) {
                model.addAttribute("user", null);
            }
        } else {
            model.addAttribute("user", null);
        }

        return "about";
    }

    @GetMapping("/contact")
    public String contact(Model model, Authentication authentication) {
        model.addAttribute("pageTitle", "Contact Us - Volmaghreb");
        model.addAttribute("currentPage", "contact");

        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            try {
                model.addAttribute("user", userService.findByEmail(authentication.getName()));
            } catch (Exception e) {
                model.addAttribute("user", null);
            }
        } else {
            model.addAttribute("user", null);
        }

        return "contact";
    }
}
