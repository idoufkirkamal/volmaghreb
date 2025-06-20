package com.volmaghreb.reservation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.volmaghreb.reservation.enums.SeatClass;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "seats")
@Data
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private SeatClass seatClass;
    private String seatNumber;
    private boolean isAvailable;

    @OneToOne(mappedBy = "seat",  cascade = CascadeType.ALL)
    @JsonIgnore
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    @JsonIgnore
    private Flight flight;
}
