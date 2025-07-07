package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.dtos.FlightDto;
import com.volmaghreb.reservation.dtos.ReservationDto;
import com.volmaghreb.reservation.dtos.ReservationRequest;
import com.volmaghreb.reservation.dtos.TravelerInfo;
import com.volmaghreb.reservation.entities.Reservation;
import com.volmaghreb.reservation.services.FlightService;
import com.volmaghreb.reservation.services.ReservationService;
import com.volmaghreb.reservation.services.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/reservations")
public class ReservationViewController {
    private ReservationService reservationService;
    private FlightService flightService;
    private UserServiceImpl userService;

    @GetMapping("")
    public String getReservations(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<ReservationDto> reservations = reservationService.getReservations(page, size);
        model.addAttribute("reservations", reservations);
        model.addAttribute("pageTitle", "Reservations - Volmaghreb");
        return "reservations/flight-list";
    }
    @GetMapping("/book")
    public String showBookingForm(Model model, @RequestParam Long flightId, @RequestParam(defaultValue = "1") int travelers, @RequestParam(required=false) String travelClass, Authentication authentication) {
        FlightDto flight = flightService.getFlightDtoById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        model.addAttribute("flight", flight);
        model.addAttribute("pageTitle", "Book Flight - Volmaghreb");

        ReservationRequest reservationRequest = new ReservationRequest();
        List<TravelerInfo> travelerList = new ArrayList<>();
        for (int i = 0; i < travelers; i++) {
            travelerList.add(new TravelerInfo());
        }
        reservationRequest.setTravelers(travelerList);
        reservationRequest.setFlightId(flightId);
        reservationRequest.setTravelClass(travelClass != null ? travelClass : "ECONOMY");

        model.addAttribute("reservationDto", reservationRequest);
        model.addAttribute("travelers", travelers);
        model.addAttribute("travelClass", travelClass != null ? travelClass : "ECONOMY");
        model.addAttribute("pageTitle", "Book Flight - Volmaghreb");
        
        // Add user information if authenticated
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            try {
                model.addAttribute("user", userService.findByEmail(authentication.getName()));
            } catch (Exception e) {
                model.addAttribute("user", null);
            }
        } else {
            model.addAttribute("user", null);
        }
        
        return "reservations/reservation-detail";
    }    @PostMapping("/book")
    public String createReservation(@ModelAttribute("reservationDto") ReservationRequest reservationRequest, Model model, Authentication authentication) {
        try {
            // Validate the request
            if (reservationRequest.getTravelers() == null || reservationRequest.getTravelers().isEmpty()) {
                throw new RuntimeException("At least one traveler is required");
            }
            
            // Validate each traveler
            for (int i = 0; i < reservationRequest.getTravelers().size(); i++) {
                TravelerInfo traveler = reservationRequest.getTravelers().get(i);
                if (traveler.getFirstName() == null || traveler.getFirstName().trim().isEmpty()) {
                    throw new RuntimeException("First name is required for traveler " + (i + 1));
                }
                if (traveler.getLastName() == null || traveler.getLastName().trim().isEmpty()) {
                    throw new RuntimeException("Last name is required for traveler " + (i + 1));
                }
                if (traveler.getDateOfBirth() == null) {
                    throw new RuntimeException("Date of birth is required for traveler " + (i + 1));
                }
                if (traveler.getNationality() == null || traveler.getNationality().trim().isEmpty()) {
                    throw new RuntimeException("Nationality is required for traveler " + (i + 1));
                }
                if (traveler.getPassportNumber() == null || traveler.getPassportNumber().trim().isEmpty()) {
                    throw new RuntimeException("Passport number is required for traveler " + (i + 1));
                }
            }
            
            List<Reservation> reservations = reservationService.createReservation(reservationRequest);
            model.addAttribute("reservations", reservations);
            model.addAttribute("bookingSuccess", true);
            model.addAttribute("pageTitle", "Booking Confirmation - Volmaghreb");
            
            // Add user information if authenticated
            if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
                try {
                    model.addAttribute("user", userService.findByEmail(authentication.getName()));
                } catch (Exception e) {
                    model.addAttribute("user", null);
                }
            } else {
                model.addAttribute("user", null);
            }
            
            return "reservations/reservation-booking";
        } catch (Exception e) {
            // When there's an error, we need to reload the flight data for the template
            FlightDto flight = flightService.getFlightDtoById(reservationRequest.getFlightId())
                    .orElseThrow(() -> new RuntimeException("Flight not found"));
            model.addAttribute("flight", flight);
            model.addAttribute("reservationDto", reservationRequest);
            model.addAttribute("travelers", reservationRequest.getTravelers().size());
            model.addAttribute("travelClass", reservationRequest.getTravelClass());
            model.addAttribute("error", e.getMessage());
            model.addAttribute("pageTitle", "Book Flight - Volmaghreb");
            
            // Add user information if authenticated
            if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
                try {
                    model.addAttribute("user", userService.findByEmail(authentication.getName()));
                } catch (Exception ex) {
                    model.addAttribute("user", null);
                }
            } else {
                model.addAttribute("user", null);
            }
            
            return "reservations/reservation-detail";
        }
    }
    

    @GetMapping("/my-reservations")
    public String getUserReservations(Model model, Authentication authentication) {
        List<ReservationDto> reservations = reservationService.getReservationsForCurrentUser();
        model.addAttribute("reservations", reservations);
        model.addAttribute("pageTitle", "My Reservations - Volmaghreb");
        
        // Add user information if authenticated
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            try {
                model.addAttribute("user", userService.findByEmail(authentication.getName()));
            } catch (Exception e) {
                model.addAttribute("user", null);
            }
        } else {
            model.addAttribute("user", null);
        }
        
        return "reservations/reservation-account";
    }

    @GetMapping("/detail/{id}")
    public String showReservationDetail(@PathVariable Long id, Model model, Authentication authentication) {
        try {
            ReservationDto reservation = reservationService.getReservationById(id);
            model.addAttribute("reservation", reservation);
            model.addAttribute("pageTitle", "Reservation Details - Volmaghreb");
            
            // Add user information if authenticated
            if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
                try {
                    model.addAttribute("user", userService.findByEmail(authentication.getName()));
                } catch (Exception e) {
                    model.addAttribute("user", null);
                }
            } else {
                model.addAttribute("user", null);
            }
            
            return "reservations/reservation-detail";
        } catch (Exception e) {
            // If reservation not found or access denied, redirect to my-reservations
            return "redirect:/reservations/my-reservations";
        }
    }

}
