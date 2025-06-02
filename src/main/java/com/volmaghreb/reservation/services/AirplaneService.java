package com.volmaghreb.reservation.services;

import com.volmaghreb.reservation.entities.Airplane;
import java.util.List;
import java.util.Optional;

public interface AirplaneService {
    List<Airplane> getAllAirplanes();
    Optional<Airplane> getAirplaneById(Long id);
    Airplane saveAirplane(Airplane airplane);
    void deleteAirplane(Long id);
}
