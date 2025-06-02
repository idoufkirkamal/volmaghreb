package com.volmaghreb.reservation.repositories;

import com.volmaghreb.reservation.entities.aireport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AireportRepository extends JpaRepository<aireport, Long> {
    // Custom query methods can be added here if needed
}
