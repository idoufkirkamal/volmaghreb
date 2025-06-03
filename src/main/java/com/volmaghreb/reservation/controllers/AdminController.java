package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.entities.Airplane;
import com.volmaghreb.reservation.entities.Airport;
import com.volmaghreb.reservation.entities.Flight;
import com.volmaghreb.reservation.services.AirplaneService;
import com.volmaghreb.reservation.services.AirportService;
import com.volmaghreb.reservation.services.FlightService;
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
    
    @Autowired
    private AirportService airportService;
    
    @Autowired
    private FlightService flightService;

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
        return "admin/airplanes";
    }

    @GetMapping("/flights")
    public String adminFlights(Model model) {
        List<Flight> flights = flightService.getAllFlights();
        List<Airport> airports = airportService.getAllAirports();
        List<Airplane> airplanes = airplaneService.getAllAirplanes();
        
        model.addAttribute("flights", flights);
        model.addAttribute("airports", airports);
        model.addAttribute("airplanes", airplanes);
        model.addAttribute("pageTitle", "Flight Management - Booking");
        return "admin/flights";
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
        List<Airport> airports = airportService.getAllAirports();
        model.addAttribute("airports", airports);
        model.addAttribute("pageTitle", "Airport Management - Booking");
        return "admin/airports";
    }
}
