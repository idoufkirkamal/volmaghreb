package com.volmaghreb.reservation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "iataCode"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 3, nullable = false)
    private String iataCode;
    private String name;
    private String city;
    private String country;

    @OneToMany(mappedBy = "originAirport", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Flight> departures;

    @OneToMany(mappedBy = "destinationAirport", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Flight> arrivals;
}
