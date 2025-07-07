package com.volmaghreb.reservation.repositories;

import com.volmaghreb.reservation.entities.Flight;
import com.volmaghreb.reservation.entities.Seat;
import com.volmaghreb.reservation.enums.SeatClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByFlightAndIsAvailable(Flight flight, boolean isAvailable);
    List<Seat> findByFlightAndSeatClassAndIsAvailable(Flight flight, SeatClass seatClass, boolean isAvailable);
    boolean existsByFlight(Flight flight);
    List<Seat> findByFlight(Flight flight);
    long countByFlight(Flight flight);
}
