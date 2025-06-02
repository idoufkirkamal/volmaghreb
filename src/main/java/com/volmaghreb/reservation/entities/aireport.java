package com.volmaghreb.reservation.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "aireports")
public class Aireport {    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  
    
    @Column(unique = true, nullable = false)
    private String code;
    private String name;
    private String city;
    private String country;

    
    
}
