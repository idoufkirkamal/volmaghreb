package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.entities.Airplane;
import com.volmaghreb.reservation.entities.Airport;
import com.volmaghreb.reservation.services.AirplaneService;
import com.volmaghreb.reservation.services.AirportService;
import com.volmaghreb.reservation.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AirplaneService airplaneService;
    
    @Autowired
    private AirportService airportService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("pageTitle", "Admin Dashboard - Booking");
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String adminUsers(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("pageTitle", "User Management - Booking");
        model.addAttribute("page", userService.getAll(page, 5));
        return "admin/admin-users";
    }

    @GetMapping("/airplanes")
    public String adminAirplanes(Model model) {
        List<Airplane> airplanes = airplaneService.getAllAirplanes();
        model.addAttribute("airplanes", airplanes);
        model.addAttribute("pageTitle", "Airplane Management - Booking");
        return "admin/airplanes";
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

    @GetMapping("/airports")
    public String adminAirports(Model model) {
        List<Airport> airports = airportService.getAllAirports();
        model.addAttribute("airports", airports);
        model.addAttribute("pageTitle", "Airport Management - Booking");
        return "admin/airports";
    }
}
