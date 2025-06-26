package com.volmaghreb.reservation.dtos;

import lombok.Data;

@Data
public class AirportDto {
    private Long id;
    private String iataCode;
    private String code; // Alias for iataCode for compatibility
    private String name;
    private String city;
    private String country;
    
    // Getter for code to maintain compatibility
    public String getCode() {
        return iataCode;
    }
    
    // Setter for code to maintain compatibility
    public void setCode(String code) {
        this.iataCode = code;
    }
}
