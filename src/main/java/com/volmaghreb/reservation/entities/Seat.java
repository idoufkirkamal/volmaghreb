package com.volmaghreb.reservation.entities;

import com.volmaghreb.reservation.enums.SeatClass;
import org.volmaghreb.reservation.entities.Flight;
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
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;
}
