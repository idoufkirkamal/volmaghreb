package com.volmaghreb.reservation.repositories;

import com.volmaghreb.reservation.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    long countByUserId(Long userId);
}
