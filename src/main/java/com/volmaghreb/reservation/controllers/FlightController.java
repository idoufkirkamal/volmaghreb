package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.entities.Flight;
import com.volmaghreb.reservation.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "*")
public class FlightController {
    
    @Autowired
    private FlightService flightService;
    
    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Optional<Flight> flight = flightService.getFlightById(id);
        return flight.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/flight-number/{flightNumber}")
    public ResponseEntity<Flight> getFlightByFlightNumber(@PathVariable String flightNumber) {
        Optional<Flight> flight = flightService.getFlightByFlightNumber(flightNumber);
        return flight.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Flight createFlight(@RequestBody Flight flight) {
        return flightService.saveFlight(flight);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        flight.setId(id);
        Flight updatedFlight = flightService.saveFlight(flight);
        return ResponseEntity.ok(updatedFlight);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public List<Flight> searchFlights(@RequestParam String searchTerm) {
        return flightService.searchFlights(searchTerm);
    }
    
    @GetMapping("/generate-flight-number")
    public ResponseEntity<String> generateFlightNumber() {
        return ResponseEntity.ok(flightService.generateFlightNumber());
    }
}
