package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.entities.Flight;
import com.volmaghreb.reservation.services.AirportService;
import com.volmaghreb.reservation.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/flights")
public class FlightWebController {

    @Autowired
    private FlightService flightService;
    
    @Autowired
    private AirportService airportService;

    @GetMapping("")
    public String flightIndex(Model model) {
        model.addAttribute("pageTitle", "Flight Search - Volmaghreb");
        model.addAttribute("airports", airportService.getAllAirports());
        return "flights/index";
    }    @GetMapping("/search")
    public String searchFlights(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) String departureDate,
            @RequestParam(required = false) Integer travelers,
            Model model) {
        
        model.addAttribute("pageTitle", "Flight Results - Volmaghreb");
        model.addAttribute("airports", airportService.getAllAirports());
        
        // Add search parameters to model for form persistence
        model.addAttribute("searchFrom", from);
        model.addAttribute("searchTo", to);
        model.addAttribute("searchDepartureDate", departureDate);
        model.addAttribute("searchTravelers", travelers);
        
        List<Flight> flights = null;
        
        try {
            Long fromAirportId = null;
            Long toAirportId = null;
            LocalDate searchDate = null;
            
            // Parse parameters
            if (from != null && !from.isEmpty()) {
                fromAirportId = Long.parseLong(from);
            }
            if (to != null && !to.isEmpty()) {
                toAirportId = Long.parseLong(to);
            }
            if (departureDate != null && !departureDate.isEmpty()) {
                try {
                    // Try parsing as "d M yyyy" format first (from flatpickr)
                    searchDate = LocalDate.parse(departureDate, DateTimeFormatter.ofPattern("d M yyyy"));
                } catch (Exception e) {
                    try {
                        // Try parsing as ISO date format (yyyy-MM-dd)
                        searchDate = LocalDate.parse(departureDate);
                    } catch (Exception e2) {
                        // If both fail, ignore the date filter
                        searchDate = null;
                    }
                }
            }
            
            // Use enhanced search method
            flights = flightService.searchFlights(fromAirportId, toAirportId, searchDate);
            
        } catch (Exception e) {
            // If there's an error, show all flights
            flights = flightService.getAllFlights();
        }
        
        model.addAttribute("flights", flights);
        model.addAttribute("flightCount", flights != null ? flights.size() : 0);
        
        return "flights/flight-list";
    }
}
