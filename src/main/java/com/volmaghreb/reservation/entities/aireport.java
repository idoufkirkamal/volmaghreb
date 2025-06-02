package com.volmaghreb.reservation.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class aireport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(unique = true, nullable = false)
    private String code;
}
