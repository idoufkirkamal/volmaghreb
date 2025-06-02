package com.volmaghreb.reservation.services;

import com.volmaghreb.reservation.entities.aireport;
import com.volmaghreb.reservation.repositories.AireportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AireportService {

    @Autowired
    private AireportRepository aireportRepository;

    public List<aireport> getAllAireports() {
        return aireportRepository.findAll();
    }

    public Optional<aireport> getAireportById(Long id) {
        return aireportRepository.findById(id);
    }

    public aireport saveAireport(aireport aireport) {
        return aireportRepository.save(aireport);
    }

    public void deleteAireport(Long id) {
        aireportRepository.deleteById(id);
    }
}
