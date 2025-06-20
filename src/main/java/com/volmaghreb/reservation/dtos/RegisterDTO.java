package com.volmaghreb.reservation.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {
    
    @NotEmpty(message = "Le prénom est obligatoire")
    private String firstname;
    
    @NotEmpty(message = "Le nom est obligatoire")
    private String lastname;
    
    @NotEmpty(message = "L'email est obligatoire")
    @Email(message = "Veuillez fournir une adresse email valide")
    private String email;
    
    private String phone;
    
    @NotEmpty(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;
    
    @NotEmpty(message = "La confirmation du mot de passe est obligatoire")
    private String confirmPassword;
} 