package com.volmaghreb.reservation.repositories;

import com.volmaghreb.reservation.entities.Reservation;
import com.volmaghreb.reservation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    long countByUserId(Long userId);
    List<Reservation> findByUser(User user);
}
