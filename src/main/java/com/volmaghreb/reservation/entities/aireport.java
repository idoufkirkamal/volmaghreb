package com.volmaghreb.reservation.entities;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Entity
@SpringBootApplication
public class aireport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(unique = true, nullable = false)
    private String code;

    // Default constructor
    public aireport() {}

    // Parameterized constructor
    public aireport(String name, String location, String code) {
        this.name = name;
        this.location = location;
        this.code = code;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "aireport{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
