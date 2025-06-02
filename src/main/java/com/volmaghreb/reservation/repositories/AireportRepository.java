package com.volmaghreb.reservation.repositories;

import com.volmaghreb.reservation.entities.Aireport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AireportRepository extends JpaRepository<Aireport, Long> {
    // Custom query methods can be added here if needed
}
