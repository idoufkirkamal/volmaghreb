package com.volmaghreb.reservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // Resources publiques
                .requestMatchers("/assets/**", "/webjars/**", "/", "/auth/**", "/register", "/error/**").permitAll()
                
                // Routes réservées aux administrateurs
                .requestMatchers("/admin/**", "/dashboard/**").hasRole("ADMIN")
                
                // Routes réservées aux utilisateurs
                .requestMatchers("/user/**", "/home/**", "/reservations/**", "/flights/**", "/profile/**").hasRole("USER")
                
                // Tout autre accès nécessite une authentification
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/auth/sign-in")
                .loginProcessingUrl("/login")
                .usernameParameter("username")  // Correspond au nom du champ dans le formulaire
                .passwordParameter("password")  // Correspond au nom du champ dans le formulaire
                .successHandler((request, response, authentication) -> {
                    // Redirection basée sur le rôle
                    if (authentication.getAuthorities().stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                        response.sendRedirect("/volmaghreb/dashboard");
                    } else {
                        response.sendRedirect("/volmaghreb/home");
                    }
                })
                .failureUrl("/auth/sign-in?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/auth/sign-in?logout=true")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/access-denied")
            )
            .rememberMe(remember -> remember
                .key("volmaghrebUniqueKey")
                .tokenValiditySeconds(86400) // 1 jour
                .rememberMeParameter("remember-me") // Correspond au nom du champ dans le formulaire
            );

        return http.build();
    }
}
