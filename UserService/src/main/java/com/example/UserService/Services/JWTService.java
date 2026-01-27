package com.example.UserService.Services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.UserService.Entities.RefreshToken;
import com.example.UserService.Repository.RefreshTokenRepository;
import com.example.UserService.utils.TokenExpiredException;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Service
@Slf4j
public class JWTService {

    @Value("${DATA.JWT_SECRET}")
    private String secret; // Injected AFTER object creation

    private SecretKey SECRET_KEY; // Define but don't initialize immediately

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    // Initialize key AFTER `secret` is injected
    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        SECRET_KEY = Keys.hmacShaKeyFor(keyBytes);
    }

    public Map<String, String> generateToken(String username, List<String> roles) {
        Map<String, String> tokens = new HashMap<String, String>();

        String access_token = Jwts.builder()
                .setSubject(username) // Set the username
                .setIssuedAt(new Date()) // Set issue time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expiry: 1 hour= 1000 * 60 * 60
                                                                                      // * 24
                // now its 1 min
                .signWith(SECRET_KEY) // Sign with generated key

                .claim("type", "access")
                .addClaims(Map.of("roles", roles)) // Add roles claim
                .compact();

        String jti = UUID.randomUUID().toString();

        String refresh_token = Jwts.builder()
                .setSubject(username) // same user
                .setId(jti) // jti
                .setIssuedAt(new Date())
                .setExpiration(new Date(
                        System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7)) // 7 days
                .claim("type", "refresh") // mark as refresh
                .signWith(SECRET_KEY)
                .compact();

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .username(username)
                .jti(jti)
                .tokenHash(hash(refresh_token)) // NEVER store plain token
                .expiresAt(Instant.now().plus(7, ChronoUnit.DAYS))
                .isRevoked(false)
                .build();

        refreshTokenRepository.save(refreshTokenEntity);

        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        System.out.println("token are\n" + access_token + "\n" + refresh_token + "\n");
        return tokens;
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

            if (username.equals(userDetails.getUsername()) == false) {
                log.error("Invalid JWT Token: {}");
                throw new RuntimeException("Invalid JWT Token");
            }
            if (expiration.after(new Date())) {
                throw new TokenExpiredException("Access Token expired");
            }
            return true;
        } catch (Exception e) {
            log.error("Invalid JWT Token: {}", e.getMessage());
            throw new RuntimeException("Invalid JWT Token: {} " + e.getMessage());

        }
    }

    public static String hash(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashed);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing token", e);
        }
    }

    public String generateAccessToken(String username, List<String> roles) {

        String access_token = Jwts.builder()
                .setSubject(username) // Set the username
                .setIssuedAt(new Date()) // Set issue time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expiry: 1 hour= 1000 * 60 * 60
                                                                                      // * 24
                // now its 1 min
                .signWith(SECRET_KEY) // Sign with generated key

                .claim("type", "access")
                .addClaims(Map.of("roles", roles)) // Add roles claim
                .compact();

        return access_token;
    }

    public String generateRefreshToken(String username, String jti) {
        return Jwts.builder()
                .setSubject(username)
                .setId(jti)
                .claim("type", "refresh")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7))
                .signWith(SECRET_KEY)
                .compact();
    }

    public Claims validateRefreshToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
