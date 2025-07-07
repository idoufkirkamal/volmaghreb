package com.volmaghreb.reservation.controllers;

import com.volmaghreb.reservation.dtos.LoginDTO;
import com.volmaghreb.reservation.dtos.RegisterDTO;
import com.volmaghreb.reservation.entities.User;
import com.volmaghreb.reservation.enums.Role;
import com.volmaghreb.reservation.repositories.UserRepository;
import com.volmaghreb.reservation.services.AuthService;
import com.volmaghreb.reservation.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/auth/sign-in")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                          @RequestParam(value = "logout", required = false) String logout,
                          @RequestParam(value = "expired", required = false) String expired,
                          Model model) {
        if (error != null) {
            model.addAttribute("error", "Email ou mot de passe invalide");
        }
        if (logout != null) {
            model.addAttribute("message", "Vous avez été déconnecté avec succès");
        }
        if (expired != null) {
            model.addAttribute("expired", "Votre session a expiré. Veuillez vous reconnecter.");
        }
        
        // Vérifier si l'utilisateur est déjà connecté
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/flights";
            }
        }
        
        model.addAttribute("loginDTO", new LoginDTO());
        return "auth/sign-in";
    }
    
    @GetMapping("/register")
    public String registerPage(Model model) {
        // Vérifier si l'utilisateur est déjà connecté
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/flights";
            }
        }
        
        model.addAttribute("registerDTO", new RegisterDTO());
        return "auth/sign-up";
    }
    
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute RegisterDTO registerDTO, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes,
                              Model model) {
        // Validation des données
        if (result.hasErrors()) {
            model.addAttribute("error", "Please correct the errors in the form");
            model.addAttribute("registerDTO", registerDTO);
            return "auth/sign-up";
        }
        
        // Vérifier si l'email existe déjà
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            model.addAttribute("error", "This email is already in use");
            model.addAttribute("registerDTO", registerDTO);
            return "auth/sign-up";
        }
        
        // Vérifier si les mots de passe correspondent
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match");
            model.addAttribute("registerDTO", registerDTO);
            return "auth/sign-up";
        }
        
        // Créer un nouvel utilisateur
        User user = new User();
        user.setFirstname(registerDTO.getFirstname());
        user.setLastname(registerDTO.getLastname());
        user.setEmail(registerDTO.getEmail());
        user.setPhone(registerDTO.getPhone());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRole(Role.ROLE_CLIENT);
        
        // Enregistrer l'utilisateur
        userRepository.save(user);
        
        // Rediriger vers la page de connexion
        redirectAttributes.addFlashAttribute("success", "Your account has been created successfully. Please log in.");
        return "redirect:/auth/sign-in";
    }
    
    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        // Add user information if authenticated
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            try {
                model.addAttribute("user", userService.findByEmail(authentication.getName()));
            } catch (Exception e) {
                model.addAttribute("user", null);
            }
        } else {
            model.addAttribute("user", null);
        }
        
        return "user/home";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied";
    }
} 