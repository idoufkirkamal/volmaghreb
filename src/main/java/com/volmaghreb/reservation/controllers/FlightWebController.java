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
            @RequestParam(required = false) String travelClass,
            @RequestParam(required = false) Integer travelers,
            Model model) {
        
        model.addAttribute("pageTitle", "Flight Results - Volmaghreb");
        model.addAttribute("airports", airportService.getAllAirports());
        
        // Add search parameters to model for form persistence
        model.addAttribute("searchFrom", from);
        model.addAttribute("searchTo", to);
        model.addAttribute("searchDepartureDate", departureDate);
        model.addAttribute("searchTravelClass", travelClass);
        model.addAttribute("searchTravelers", travelers);
        
        // Add formatted travel class for display
        if (travelClass != null && !travelClass.isEmpty()) {
            String formattedTravelClass = formatTravelClass(travelClass);
            model.addAttribute("searchTravelClassFormatted", formattedTravelClass);
        }
        
        List<Flight> flights = null;
        Long fromAirportId = null;
        Long toAirportId = null;
        LocalDate searchDate = null;
        
        // Debug logging
        System.out.println("Flight search parameters:");
        System.out.println("  From: " + from);
        System.out.println("  To: " + to);
        System.out.println("  Departure Date: " + departureDate);
        System.out.println("  Travel Class: " + travelClass);
        System.out.println("  Travelers: " + travelers);
        
        try {
            Integer requiredTravelers = travelers != null ? travelers : 1;
            
            // Parse parameters
            if (from != null && !from.isEmpty()) {
                fromAirportId = Long.parseLong(from);
            }
            if (to != null && !to.isEmpty()) {
                toAirportId = Long.parseLong(to);
            }
            if (departureDate != null && !departureDate.isEmpty()) {
                try {
                    // Handle various date formats that could come from the flatpickr component
                    // Try parsing as "d M yyyy" format first (from flatpickr with Y format: "6 Jan 2025")
                    searchDate = LocalDate.parse(departureDate, DateTimeFormatter.ofPattern("d MMM yyyy"));
                } catch (Exception e) {
                    try {
                        // Try parsing as "d M yyyy" format with short month names: "6 Jan 2025" 
                        searchDate = LocalDate.parse(departureDate, DateTimeFormatter.ofPattern("d M yyyy"));
                    } catch (Exception e2) {
                        try {
                            // Try parsing as ISO date format (yyyy-MM-dd)
                            searchDate = LocalDate.parse(departureDate);
                        } catch (Exception e3) {
                            try {
                                // Try with short date format (d M yy for 2-digit year)
                                searchDate = LocalDate.parse(departureDate, DateTimeFormatter.ofPattern("d M yy"));
                            } catch (Exception e4) {
                                try {
                                    // Try with "dd/MM/yyyy" format
                                    searchDate = LocalDate.parse(departureDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                } catch (Exception e5) {
                                    try {
                                        // Try with "MM/dd/yyyy" format
                                        searchDate = LocalDate.parse(departureDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                                    } catch (Exception e6) {
                                        // Log the error and ignore the date filter
                                        System.err.println("Could not parse departure date: " + departureDate + ". Error: " + e6.getMessage());
                                        searchDate = null;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            // Debug the parsed values
            System.out.println("Parsed values:");
            System.out.println("  From Airport ID: " + fromAirportId);
            System.out.println("  To Airport ID: " + toAirportId);
            System.out.println("  Search Date: " + searchDate);
            
            // Use enhanced search method with seat availability check
            flights = flightService.searchFlightsWithAvailability(fromAirportId, toAirportId, searchDate, travelClass, requiredTravelers);
            
            System.out.println("Found " + (flights != null ? flights.size() : 0) + " flights matching criteria");
            
        } catch (Exception e) {
            // If there's an error, show all flights
            System.err.println("Error in flight search: " + e.getMessage());
            e.printStackTrace();
            flights = flightService.getAllFlights();
        }
        
        // Calculate available seats for each flight based on selected travel class
        System.out.println("Travel class parameter: '" + travelClass + "'");
        if (flights != null && travelClass != null && !travelClass.isEmpty()) {
            System.out.println("Calculating class-specific seats for travel class: " + travelClass);
            for (Flight flight : flights) {
                int availableSeats = getAvailableSeatsForClass(flight, travelClass);
                System.out.println("Flight " + flight.getFlightNumber() + " - Total seats for " + travelClass + ": " + availableSeats);
                // Store the available seats in a transient field for display
                flight.setAvailableSeats(availableSeats);
            }
        } else {
            System.out.println("No travel class specified, flights will show total available seats");
        }
        
        model.addAttribute("flights", flights);
        model.addAttribute("flightCount", flights != null ? flights.size() : 0);
        
        // Add airport names for display
        if (fromAirportId != null) {
            airportService.getAirportById(fromAirportId).ifPresent(airport -> 
                model.addAttribute("searchFromName", airport.getCity() + ", " + airport.getCountry()));
        }
        if (toAirportId != null) {
            airportService.getAirportById(toAirportId).ifPresent(airport -> 
                model.addAttribute("searchToName", airport.getCity() + ", " + airport.getCountry()));
        }
        
        return "flights/flight-list";
    }
    
    private int getAvailableSeatsForClass(Flight flight, String travelClass) {
        System.out.println("Getting available seats for flight " + flight.getFlightNumber() + " in class: " + travelClass);
        if (flight.getAirplane() == null) {
            return 0;
        }
        
        int totalSeats = 0;
        int bookedSeats = 0;
        
        switch (travelClass.toUpperCase()) {
            case "FIRST_CLASS":
                totalSeats = flight.getAirplane().getFirstClassCapacity();
                bookedSeats = getBookedSeatsForClass(flight, "FIRST_CLASS");
                break;
            case "BUSINESS":
            case "BUSINESS_CLASS":
                totalSeats = flight.getAirplane().getBusinessClassCapacity();
                bookedSeats = getBookedSeatsForClass(flight, "BUSINESS_CLASS");
                break;
            case "ECONOMY":
            case "ECONOMY_CLASS":
                totalSeats = flight.getAirplane().getEconomyClassCapacity();
                bookedSeats = getBookedSeatsForClass(flight, "ECONOMY_CLASS");
                break;
            default:
                System.out.println("Unrecognized travel class: " + travelClass + ", returning total available seats");
                return flight.getAvailableSeats(); // Return total available seats if class not recognized
        }
        
        int availableSeats = Math.max(0, totalSeats - bookedSeats);
        System.out.println("Class " + travelClass + " - Total: " + totalSeats + ", Booked: " + bookedSeats + ", Available: " + availableSeats);
        return availableSeats;
    }
    
    private int getBookedSeatsForClass(Flight flight, String seatClass) {
        if (flight.getReservations() == null) {
            return 0;
        }
        
        return (int) flight.getReservations().stream()
            .filter(reservation -> reservation.getSeat() != null && 
                                 reservation.getSeat().getSeatClass().name().equals(seatClass))
            .count();
    }
    
    /**
     * Formats travel class for display purposes
     * @param travelClass the raw travel class (e.g., "FIRST_CLASS", "BUSINESS", "ECONOMY")
     * @return formatted travel class (e.g., "First class", "Business", "Economy")
     */
    private String formatTravelClass(String travelClass) {
        if (travelClass == null || travelClass.isEmpty()) {
            return "";
        }
        
        switch (travelClass.toUpperCase()) {
            case "FIRST_CLASS":
                return "First class";
            case "BUSINESS":
            case "BUSINESS_CLASS":
                return "Business class";
            case "ECONOMY":
            case "ECONOMY_CLASS":
                return "Economy class";
            default:
                // For any other values, just capitalize first letter and lowercase the rest
                return travelClass.substring(0, 1).toUpperCase() + 
                       travelClass.substring(1).toLowerCase().replace("_", " ");
        }
    }
}
