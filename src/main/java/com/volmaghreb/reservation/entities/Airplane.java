package com.volmaghreb.reservation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private int firstClassCapacity;
    private int businessClassCapacity;
    private int economyClassCapacity;

    @OneToOne(mappedBy = "airplane",  cascade = CascadeType.ALL)
    @JsonIgnore
    private Flight flight;
}
