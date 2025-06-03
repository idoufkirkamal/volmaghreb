package com.volmaghreb.reservation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.volmaghreb.reservation.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private FlightStatus status = FlightStatus.ACTIVE;

    // Calculated properties for available seats
    @Transient
    public int getAvailableFirstClassSeats() {
        int reserved = 0; // No reservations, so no seats are reserved
        return airplane.getFirstClassCapacity() - reserved;
    }

    @Transient
    public int getAvailableBusinessClassSeats() {
        int reserved = 0; // No reservations, so no seats are reserved
        return airplane.getBusinessClassCapacity() - reserved;
    }

    @Transient
    public int getAvailableEconomyClassSeats() {
        int reserved = 0; // No reservations, so no seats are reserved
        return airplane.getEconomyClassCapacity() - reserved;
    }

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reservation> reservations = new ArrayList<>();

    // Custom getter for reservations
    public List<Reservation> getReservations() {
        if (reservations == null) {
            reservations = new ArrayList<>();
        }
        return reservations;
    }    // Custom setter for reservations
    public void setReservations(List<Reservation> reservations) {
        // Don't replace the existing collection during updates to avoid Hibernate cascade issues
        // Only initialize if null, otherwise keep the existing managed collection
        if (this.reservations == null) {
            this.reservations = new ArrayList<>();
        }
        // Don't clear and replace during entity updates to avoid orphan removal issues
        // The reservations should be managed through proper service methods
    }

    // Helper method to add a reservation
    public void addReservation(Reservation reservation) {
        if (reservations == null) {
            reservations = new ArrayList<>();
        }
        reservations.add(reservation);
        reservation.setFlight(this);
    }

    // Helper method to remove a reservation
    public void removeReservation(Reservation reservation) {
        if (reservations != null) {
            reservations.remove(reservation);
            reservation.setFlight(null);
        }
    }
}
