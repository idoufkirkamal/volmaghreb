package com.volmaghreb.reservation.repositories;

import com.volmaghreb.reservation.entities.Flight;
import com.volmaghreb.reservation.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    boolean existsByFlight(Flight flight);
    List<Seat> findByFlight(Flight flight);
    long countByFlight(Flight flight);
}
