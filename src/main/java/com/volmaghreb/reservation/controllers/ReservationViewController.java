package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.dtos.FlightDto;
import com.volmaghreb.reservation.dtos.ReservationDto;
import com.volmaghreb.reservation.dtos.ReservationRequest;
import com.volmaghreb.reservation.dtos.TravelerInfo;
import com.volmaghreb.reservation.services.FlightService;
import com.volmaghreb.reservation.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping("")
    public String getReservations(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<ReservationDto> reservations = reservationService.getReservations(page, size);
        model.addAttribute("reservations", reservations);
        return "reservations/reservation-list";
    }
    @GetMapping("/book")
    public String showBookingForm(Model model, @RequestParam Long flightId, @RequestParam(defaultValue = "1") int travelers) {
        FlightDto flight = flightService.getFlightDtoById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        model.addAttribute("flight", flight);

        ReservationRequest reservationRequest = new ReservationRequest();
        List<TravelerInfo> travelerList = new ArrayList<>();
        for (int i = 0; i < travelers; i++) {
            travelerList.add(new TravelerInfo());
        }
        reservationRequest.setTravelers(travelerList);
        reservationRequest.setFlightId(flightId);

        model.addAttribute("reservationDto", reservationRequest);
        model.addAttribute("travelers", travelers);
        return "reservations/reservation-detail";
    }

    @PostMapping("/book")
    public String createReservation(@ModelAttribute("reservation") ReservationRequest reservationRequest) {
        reservationService.createReservation(reservationRequest);
        return "redirect:/reservations/my-reservations";
    }
    

    @GetMapping("/my-reservations")
    public String getUserReservations(Model model) {
        List<ReservationDto> reservations = reservationService.getReservationsForCurrentUser();
        model.addAttribute("reservations", reservations);
        return "reservations/reservation-account";
    }

}
