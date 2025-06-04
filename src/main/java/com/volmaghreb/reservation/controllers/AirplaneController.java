package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.entities.Airplane;
import com.volmaghreb.reservation.services.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/airplanes")
public class AirplaneController {
    @Autowired
    private AirplaneService airplaneService;

    @GetMapping
    public List<Airplane> getAllAirplanes() {
        return airplaneService.getAllAirplanes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airplane> getAirplaneById(@PathVariable Long id) {
        Optional<Airplane> airplane = airplaneService.getAirplaneById(id);
        return airplane.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Airplane createAirplane(@RequestBody Airplane airplane) {
        return airplaneService.saveAirplane(airplane);
    }    @PutMapping("/{id}")
    public ResponseEntity<Airplane> updateAirplane(@PathVariable Long id, @RequestBody Airplane airplane) {
        airplane.setId(id);
        Airplane updatedAirplane = airplaneService.saveAirplane(airplane);
        return ResponseEntity.ok(updatedAirplane);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirplane(@PathVariable Long id) {
        airplaneService.deleteAirplane(id);
        return ResponseEntity.noContent().build();
    }
}
