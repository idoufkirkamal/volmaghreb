package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.entities.Airport;
import com.volmaghreb.reservation.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/airports")
@CrossOrigin(origins = "*")
public class AirportController {
    @Autowired
    private AirportService airportService;

    @GetMapping
    public List<Airport> getAllAirports() {
        return airportService.getAllAirports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
        Optional<Airport> airport = airportService.getAirportById(id);
        return airport.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Airport createAirport(@RequestBody Airport airport) {
        return airportService.saveAirport(airport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirport(@PathVariable Long id, @RequestBody Airport airport) {
        airport.setId(id);
        Airport updatedAirport = airportService.saveAirport(airport);
        return ResponseEntity.ok(updatedAirport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        airportService.deleteAirport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/debug/duplicates")
    public ResponseEntity<Map<String, Object>> checkDuplicates() {
        List<Airport> allAirports = airportService.getAllAirports();
        Map<String, List<Airport>> groupedByIata = allAirports.stream()
            .collect(Collectors.groupingBy(Airport::getIataCode));
        
        Map<String, List<Airport>> duplicates = groupedByIata.entrySet().stream()
            .filter(entry -> entry.getValue().size() > 1)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        
        Map<String, Object> result = new HashMap<>();
        result.put("totalAirports", allAirports.size());
        result.put("uniqueIataCodes", groupedByIata.size());
        result.put("duplicates", duplicates);
        
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/debug/remove-duplicates")
    public ResponseEntity<Map<String, Object>> removeDuplicates() {
        List<Airport> allAirports = airportService.getAllAirports();
        Map<String, List<Airport>> groupedByIata = allAirports.stream()
            .collect(Collectors.groupingBy(Airport::getIataCode));
        
        int removedCount = 0;
        for (Map.Entry<String, List<Airport>> entry : groupedByIata.entrySet()) {
            List<Airport> airports = entry.getValue();
            if (airports.size() > 1) {
                // Keep the first one, remove the rest
                for (int i = 1; i < airports.size(); i++) {
                    airportService.deleteAirport(airports.get(i).getId());
                    removedCount++;
                }
            }
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("removedDuplicates", removedCount);
        result.put("remainingAirports", airportService.getAllAirports().size());
        
        return ResponseEntity.ok(result);
    }
}
