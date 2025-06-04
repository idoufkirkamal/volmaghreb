package com.volmaghreb.reservation.services.impl;

import com.volmaghreb.reservation.entities.Airplane;
import com.volmaghreb.reservation.repositories.AirplaneRepository;
import com.volmaghreb.reservation.services.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirplaneServiceImpl implements AirplaneService {
    @Autowired
    private AirplaneRepository airplaneRepository;

    @Override
    public List<Airplane> getAllAirplanes() {
        return airplaneRepository.findAll();
    }

    @Override
    public Optional<Airplane> getAirplaneById(Long id) {
        return airplaneRepository.findById(id);
    }

    @Override
    public Airplane saveAirplane(Airplane airplane) {
        return airplaneRepository.save(airplane);
    }

    @Override
    public void deleteAirplane(Long id) {
        airplaneRepository.deleteById(id);
    }
}
