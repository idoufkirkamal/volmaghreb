package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.services.AirportService;
import com.volmaghreb.reservation.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    public String home(Model model, Authentication authentication) {
        model.addAttribute("pageTitle", "Flight Search - Volmaghreb");
        model.addAttribute("currentPage", "home");
        model.addAttribute("airports", airportService.getAllAirports());
        
        // Add authenticated user to model if logged in
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            try {
                model.addAttribute("user", userService.findByEmail(authentication.getName()));
            } catch (Exception e) {
                // User not found, continue without user data
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
        
        // Add authenticated user to model if logged in
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
        
        // Add authenticated user to model if logged in
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
