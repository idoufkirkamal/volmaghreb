package com.volmaghreb.reservation.repositories;

import com.volmaghreb.reservation.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    boolean existsByIataCode(String iataCode);
}
