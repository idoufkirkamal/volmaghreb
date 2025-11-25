package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.dtos.AdminReservationDto;
import com.volmaghreb.reservation.entities.Airplane;
import com.volmaghreb.reservation.entities.Airport;
import com.volmaghreb.reservation.entities.Flight;
import com.volmaghreb.reservation.services.AirplaneService;
import com.volmaghreb.reservation.services.AirportService;
import com.volmaghreb.reservation.services.DashboardService;
import com.volmaghreb.reservation.services.FlightService;
import com.volmaghreb.reservation.services.ReservationService;
import com.volmaghreb.reservation.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
// Aadmin controller 
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AirplaneService airplaneService;
    
    @Autowired
    private AirportService airportService;
    
    @Autowired
    private FlightService flightService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("pageTitle", "Admin Dashboard - Volmaghreb");
        
        // Add dashboard statistics
        model.addAttribute("totalFlights", dashboardService.getTotalFlightsCount());
        model.addAttribute("activeFlights", dashboardService.getActiveFlightsCount());
        model.addAttribute("totalReservations", dashboardService.getTotalReservationsCount());
        model.addAttribute("registeredUsers", dashboardService.getRegisteredUsersCount());
        
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String adminUsers(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("pageTitle", "User Management - Volmaghreb");
        model.addAttribute("page", userService.getAllClients(page, 5));
        return "admin/users";
    }

    @GetMapping("/airplanes")
    public String adminAirplanes(Model model) {
        List<Airplane> airplanes = airplaneService.getAllAirplanes();
        model.addAttribute("airplanes", airplanes);
        model.addAttribute("pageTitle", "Airplane Management - Volmaghreb");
        return "admin/airplanes";
    }

    @GetMapping("/flights")
    public String adminFlights(Model model, HttpServletRequest request) {
        model.addAttribute("flights", flightService.getAllFlights());
        model.addAttribute("airports", airportService.getAllAirports());
        model.addAttribute("airplanes", airplaneService.getAllAirplanes());
        model.addAttribute("contextPath", request.getContextPath());
        model.addAttribute("pageTitle", "Flight Management - Volmaghreb");
        return "admin/flights";
    }

    @GetMapping("/reservations")
    public String adminReservations(Model model, 
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        Page<com.volmaghreb.reservation.dtos.AdminReservationDto> reservations = reservationService.getAdminReservations(page, size);
        model.addAttribute("reservations", reservations);
        model.addAttribute("pageTitle", "Reservation Management - Volmaghreb");
        return "admin/reservations";
    }

    @GetMapping("/airports")
    public String adminAirports(Model model) {
        List<Airport> airports = airportService.getAllAirports();
        model.addAttribute("airports", airports);
        model.addAttribute("pageTitle", "Airport Management - Volmaghreb");
        return "admin/airports";
    }

    // REST endpoints for flight management
    @PostMapping("/flights")
    @ResponseBody
    public Flight createFlight(@RequestBody Flight flight) {
        return flightService.saveFlight(flight);
    }
    
    @PutMapping("/flights/{id}")
    @ResponseBody
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        try {
            Flight updatedFlight = flightService.updateFlight(id, flight);
            return ResponseEntity.ok(updatedFlight);
        } catch (RuntimeException e) {
            System.err.println("Error updating flight: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("Error updating flight: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/flights/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/flights/{id}")
    @ResponseBody
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Optional<Flight> flight = flightService.getFlightById(id);
        return flight.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/flights/generate-number")
    @ResponseBody
    public ResponseEntity<String> generateFlightNumber() {
        return ResponseEntity.ok(flightService.generateFlightNumber());
    }
    
    @GetMapping("/flights/search")
    @ResponseBody
    public List<Flight> searchFlights(@RequestParam String searchTerm) {
        return flightService.searchFlights(searchTerm);
    }
}
