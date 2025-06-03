package com.volmaghreb.reservation.services;

import com.volmaghreb.reservation.entities.Airport;
import java.util.List;
import java.util.Optional;

public interface AirportService {
    List<Airport> getAllAirports();
    Optional<Airport> getAirportById(Long id);
    Airport saveAirport(Airport airport);
    void deleteAirport(Long id);
}
