package com.volmaghreb.reservation.services;

import com.volmaghreb.reservation.enums.FlightStatus;
import com.volmaghreb.reservation.enums.Role;
import com.volmaghreb.reservation.repositories.FlightRepository;
import com.volmaghreb.reservation.repositories.ReservationRepository;
import com.volmaghreb.reservation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    public long getTotalFlightsCount() {
        return flightRepository.count();
    }    public long getActiveFlightsCount() {
        return flightRepository.findByStatus(FlightStatus.ACTIVE).size();
    }

    public long getTotalReservationsCount() {
        return reservationRepository.count();
    }

    public long getRegisteredUsersCount() {
        // Get all users with ROLE_CLIENT role using pagination to count them
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        return userRepository.findByRole(Role.ROLE_CLIENT, pageable).getTotalElements();
    }
}
