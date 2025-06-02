package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.entities.Aireport;
import com.volmaghreb.reservation.services.AireportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/aireports")
public class AireportController {

    @Autowired
    private AireportService aireportService;

    @GetMapping
    public List<Aireport> getAllAireports() {
        return aireportService.getAllAireports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aireport> getAireportById(@PathVariable Long id) {
        Optional<Aireport> aireport = aireportService.getAireportById(id);
        return aireport.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Aireport createAireport(@RequestBody Aireport aireport) {
        return aireportService.saveAireport(aireport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAireport(@PathVariable Long id) {
        aireportService.deleteAireport(id);
        return ResponseEntity.noContent().build();
    }
}
