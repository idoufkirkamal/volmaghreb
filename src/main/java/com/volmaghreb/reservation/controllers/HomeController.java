package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @Autowired
    private AirportService airportService;
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Flight Search - Volmaghreb");
        model.addAttribute("airports", airportService.getAllAirports());
        return "flights/index";
    }
}
