package com.volmaghreb.reservation.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.volmaghreb.reservation.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String flightNumber;

    @ManyToOne
    @JoinColumn(name = "origin_airport_id", nullable = false)
    private Airport originAirport;

    @ManyToOne
    @JoinColumn(name = "destination_airport_id", nullable = false)
    private Airport destinationAirport;

    @ManyToOne
    @JoinColumn(name = "airplane_id", nullable = false)
    private Airplane airplane;

    @Column(nullable = false)
    private LocalDateTime departureDateTime;

    @Column(nullable = false)
    private LocalDateTime arrivalDateTime;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal firstClassPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal businessClassPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal economyClassPrice;

    @Enumerated(EnumType.STRING)
    private FlightStatus status = FlightStatus.ACTIVE;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("flight-reservations")
    private List<Reservation> reservations;

    // Transient field for displaying class-specific available seats
    @Transient
    private Integer classSpecificAvailableSeats;

    // Utility methods
    public int getAvailableSeats() {
        // If class-specific seats are set, return those; otherwise return total available seats
        if (classSpecificAvailableSeats != null) {
            return classSpecificAvailableSeats;
        }
        
        if (airplane == null) return 0;
        int totalSeats = airplane.getFirstClassCapacity() + 
                        airplane.getBusinessClassCapacity() + 
                        airplane.getEconomyClassCapacity();
        int bookedSeats = reservations != null ? reservations.size() : 0;
        return totalSeats - bookedSeats;
    }
    
    public void setAvailableSeats(Integer seats) {
        this.classSpecificAvailableSeats = seats;
    }

    public BigDecimal getPrice() {
        // Return economy class price as default
        return economyClassPrice;
    }
}
