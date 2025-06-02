package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.entities.Airplane;
import com.volmaghreb.reservation.services.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AirplaneService airplaneService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("pageTitle", "Admin Dashboard - Booking");
        return "admin/dashboard";
    }

    @GetMapping("/airplanes")
    public String adminAirplanes(Model model) {
        List<Airplane> airplanes = airplaneService.getAllAirplanes();
        model.addAttribute("airplanes", airplanes);
        model.addAttribute("pageTitle", "Airplane Management - Booking");
        return "airplanes/admin-airplanes";
    }

    @GetMapping("/flights")
    public String adminFlights(Model model) {
        model.addAttribute("pageTitle", "Flight Management - Booking");
        return "flights/admin-flights";
    }

    @GetMapping("/reservations")
    public String adminReservations(Model model) {
        model.addAttribute("pageTitle", "Reservation Management - Booking");
        return "reservations/admin-reservations";
    }

    @GetMapping("/users")
    public String adminUsers(Model model) {
        model.addAttribute("pageTitle", "User Management - Booking");
        return "users/admin-users";
    }

    @GetMapping("/airports")
    public String adminAirports(Model model) {
        model.addAttribute("pageTitle", "Airport Management - Booking");
        return "airports/admin-airports";
    }
}
