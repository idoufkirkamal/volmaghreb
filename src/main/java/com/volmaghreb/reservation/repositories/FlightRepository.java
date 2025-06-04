package com.volmaghreb.reservation.repositories;

import com.volmaghreb.reservation.entities.Flight;
import com.volmaghreb.reservation.enums.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    
    Optional<Flight> findByFlightNumber(String flightNumber);
    
    boolean existsByFlightNumber(String flightNumber);
    
    List<Flight> findByStatus(FlightStatus status);
    
    @Query("SELECT f FROM Flight f WHERE f.originAirport.id = :originId AND f.destinationAirport.id = :destinationId")
    List<Flight> findByOriginAndDestination(@Param("originId") Long originId, @Param("destinationId") Long destinationId);
    
    @Query("SELECT f FROM Flight f WHERE f.departureDateTime >= :startDate AND f.departureDateTime <= :endDate")
    List<Flight> findByDepartureDateTimeBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT f FROM Flight f WHERE f.originAirport.iataCode = :originCode OR f.destinationAirport.iataCode = :destinationCode OR f.flightNumber LIKE %:searchTerm%")
    List<Flight> searchFlights(@Param("originCode") String originCode, @Param("destinationCode") String destinationCode, @Param("searchTerm") String searchTerm);
}
