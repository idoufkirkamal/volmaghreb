package com.volmaghreb.reservation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable()) // disables CSRF protection
                .formLogin(login -> login.disable()) // disables form login
                .httpBasic(basic -> basic.disable()); // disables HTTP Basic auth

        return http.build();
    }
}
