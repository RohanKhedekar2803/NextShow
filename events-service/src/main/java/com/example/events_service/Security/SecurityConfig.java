package com.example.events_service.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.events_service.filters.JWTFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

        @Autowired
        private JWTFilter jwtFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Fixes
                                                                                                         // session
                                                                                                         // issue

                                // 🔹 PUBLIC ROUTES (No Authentication Required)
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/eventsAPI/auditoriums/getseats/**",
                                                                "/swagger-ui.html/**",
                                                                "/swagger-ui/**", "/v3/api-docs/**")
                                                .permitAll()
                                                .anyRequest().authenticated())

                                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}