package com.example.events_service.services;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JWTService {

    @Value("${DATA.JWT_SECRET}")
    private String secret; // Injected AFTER object creation

    private SecretKey SECRET_KEY; // Define but don't initialize immediately

    // Initialize key AFTER `secret` is injected
    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        SECRET_KEY = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Set the username
                .setIssuedAt(new Date()) // Set issue time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expiry: 1 hour
                .signWith(SECRET_KEY) // Sign with generated key
                .compact();
    }

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            log.error("Error extracting username from JWT: {}", e.getMessage());
            return null;
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) throws RuntimeException {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            Date expiration = claims.getExpiration();

            return (username.equals(userDetails.getUsername()) && expiration.after(new Date()));
        } catch (Exception e) {
            log.error("Invalid JWT Token: {}", e.getMessage());
            throw new RuntimeException("Invalid JWT Token: {} " + e.getMessage());

        }
    }

    public Boolean validateToken(String token) throws RuntimeException {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expiration = claims.getExpiration();

            return (expiration.after(new Date()));
        } catch (Exception e) {
            log.error("Invalid JWT Token: {}", e.getMessage());
            throw new RuntimeException("Invalid JWT Token: {} " + e.getMessage());

        }
    }

    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("roles", List.class); // Ensure roles are stored as an array in JWT
    }

}
