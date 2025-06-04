package com.volmaghreb.reservation.repositories;

import com.volmaghreb.reservation.entities.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelerRepository extends JpaRepository<Traveler, Long> {
}
