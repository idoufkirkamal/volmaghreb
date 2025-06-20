package com.volmaghreb.reservation.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {
    
    @NotEmpty(message = "First name is required")
    private String firstname;
    
    @NotEmpty(message = "Last name is required")
    private String lastname;
    
    @NotEmpty(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;
    
    private String phone;
    
    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    
    @NotEmpty(message = "Password confirmation is required")
    private String confirmPassword;
} 