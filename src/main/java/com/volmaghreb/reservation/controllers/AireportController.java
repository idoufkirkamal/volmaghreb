package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.dtos.AirportDTO;
import com.volmaghreb.reservation.entities.Aireport;
import com.volmaghreb.reservation.services.AireportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/airports")
@CrossOrigin(origins = "*") // Enable CORS for frontend communication
public class AireportController {

    @Autowired
    private AireportService aireportService;

    @GetMapping
    public List<AirportDTO> getAllAireports() {
        return aireportService.getAllAireports().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportDTO> getAireportById(@PathVariable Long id) {
        Optional<Aireport> aireport = aireportService.getAireportById(id);
        return aireport.map(a -> ResponseEntity.ok(convertToDTO(a)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }    @GetMapping("/code/{code}")
    public ResponseEntity<AirportDTO> getAireportByCode(@PathVariable String code) {
        Optional<Aireport> aireport = aireportService.getAireportByCode(code.toUpperCase());
        return aireport.map(a -> ResponseEntity.ok(convertToDTO(a)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }@PostMapping
    public ResponseEntity<AirportDTO> createAireport(@Valid @RequestBody AirportDTO airportDTO, BindingResult bindingResult) {
        try {
            // Check for validation errors
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().build();
            }

            // Check if airport with this code already exists
            if (aireportService.getAireportByCode(airportDTO.getIataCode().toUpperCase()).isPresent()) {
                return ResponseEntity.status(409).build(); // Conflict - airport already exists
            }

            Aireport aireport = convertToEntity(airportDTO);
            aireport.setId(null); // Ensure ID is null for new entities
            aireport.setCode(aireport.getCode().toUpperCase()); // Ensure IATA code is uppercase
            Aireport savedAireport = aireportService.saveAireport(aireport);
            return ResponseEntity.status(201).body(convertToDTO(savedAireport)); // Created status
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }    @PutMapping("/{code}")
    public ResponseEntity<AirportDTO> updateAireport(@PathVariable String code, @Valid @RequestBody AirportDTO airportDTO, BindingResult bindingResult) {
        try {
            // Check for validation errors
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().build();
            }

            Optional<Aireport> existingAireport = aireportService.getAireportByCode(code.toUpperCase());
            if (existingAireport.isPresent()) {
                Aireport aireport = convertToEntity(airportDTO);
                aireport.setId(existingAireport.get().getId());
                aireport.setCode(code.toUpperCase()); // Ensure code doesn't change and is uppercase
                Aireport updatedAireport = aireportService.saveAireport(aireport);
                return ResponseEntity.ok(convertToDTO(updatedAireport));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteAireport(@PathVariable String code) {
        try {
            Optional<Aireport> aireport = aireportService.getAireportByCode(code.toUpperCase());
            if (aireport.isPresent()) {
                aireportService.deleteAireport(aireport.get().getId());
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }// Helper methods to convert between Entity and DTO
    private AirportDTO convertToDTO(Aireport aireport) {
        return new AirportDTO(
                aireport.getId(),
                aireport.getCode(), // Maps to iataCode in DTO
                aireport.getName(),
                aireport.getCity(),
                aireport.getCountry()
        );
    }
    
    private Aireport convertToEntity(AirportDTO dto) {
        Aireport aireport = new Aireport();
        aireport.setId(dto.getId());
        aireport.setCode(dto.getIataCode()); // Map iataCode from DTO to code in entity
        aireport.setName(dto.getName());
        aireport.setCity(dto.getCity());
        aireport.setCountry(dto.getCountry());
        return aireport;
    }
}
