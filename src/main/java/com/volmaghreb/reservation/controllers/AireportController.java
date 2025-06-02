package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.entities.aireport;
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
    public List<aireport> getAllAireports() {
        return aireportService.getAllAireports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<aireport> getAireportById(@PathVariable Long id) {
        Optional<aireport> aireport = aireportService.getAireportById(id);
        return aireport.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public aireport createAireport(@RequestBody aireport aireport) {
        return aireportService.saveAireport(aireport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAireport(@PathVariable Long id) {
        aireportService.deleteAireport(id);
        return ResponseEntity.noContent().build();
    }
}
