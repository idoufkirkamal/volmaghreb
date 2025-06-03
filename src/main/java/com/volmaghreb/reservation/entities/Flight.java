package com.volmaghreb.reservation.entities;

import com.volmaghreb.reservation.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private FlightStatus status = FlightStatus.ACTIVE;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;    // Calculated properties for available seats
    @Transient
    public int getAvailableFirstClassSeats() {
        int reserved = reservations != null ? reservations.stream()
                .mapToInt(r -> r.getSeatClass() != null && r.getSeatClass().name().equals("FIRST") ? 1 : 0)
                .sum() : 0;
        return airplane.getFirstClassCapacity() - reserved;
    }

    @Transient
    public int getAvailableBusinessClassSeats() {
        int reserved = reservations != null ? reservations.stream()
                .mapToInt(r -> r.getSeatClass() != null && r.getSeatClass().name().equals("BUSINESS") ? 1 : 0)
                .sum() : 0;
        return airplane.getBusinessClassCapacity() - reserved;
    }

    @Transient
    public int getAvailableEconomyClassSeats() {
        int reserved = reservations != null ? reservations.stream()
                .mapToInt(r -> r.getSeatClass() != null && r.getSeatClass().name().equals("ECONOMY") ? 1 : 0)
                .sum() : 0;
        return airplane.getEconomyClassCapacity() - reserved;
    }
}
