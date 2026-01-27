package com.example.UserService.Services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.UserService.Entities.RefreshToken;
import com.example.UserService.Repository.RefreshTokenRepository;
import com.example.UserService.utils.InvalidRefreshTokenException;
import com.example.UserService.utils.TokenExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JWTService jwtService;

    public List<Map<String, String>> refresh(String refreshToken) {

        // 1️⃣ Verify refresh JWT (signature + expiry)
        Claims claims;
        try {
            claims = jwtService.validateRefreshToken(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Refresh Token is Expired. Login Again");
        } catch (JwtException e) {
            throw new InvalidRefreshTokenException("Refresh Token is invalid");
        }

        // 2️⃣ Validate token type
        String type = claims.get("type", String.class);
        if (!"refresh".equals(type)) {
            throw new InvalidRefreshTokenException("Refresh Token is invalid");
        }

        // 3️⃣ Extract required claims
        String username = claims.getSubject();
        String jti = claims.getId();

        if (jti == null || username == null) {
            throw new InvalidRefreshTokenException("Refresh Token is invalid");
        }

        // 4️⃣ Load refresh token from DB
        RefreshToken storedToken = refreshTokenRepository.findByJti(jti);

        // 5️⃣ Server-side validation
        if (storedToken.isRevoked()) {
            throw new InvalidRefreshTokenException("Refresh Token is invalid");
        }

        if (storedToken.getExpiresAt().isBefore(Instant.now())) {
            throw new TokenExpiredException("Refresh Token is Expired. Login Again");
        }

        // 6️⃣ Compare token hash
        String incomingHash = JWTService.hash(refreshToken);
        if (!incomingHash.equals(storedToken.getTokenHash())) {
            throw new InvalidRefreshTokenException("Refresh Token is invalid");
        }

        // 7️⃣ ROTATE refresh token (MANDATORY)
        storedToken.setRevoked(true);
        refreshTokenRepository.save(storedToken);

        // 8️⃣ Create new refresh token
        String newJti = UUID.randomUUID().toString();
        String newRefreshToken = jwtService.generateRefreshToken(username, newJti);

        RefreshToken newTokenEntity = RefreshToken.builder()
                .username(username)
                .jti(newJti)
                .tokenHash(JWTService.hash(newRefreshToken))
                .expiresAt(Instant.now().plus(7, ChronoUnit.DAYS))
                .isRevoked(false)
                .build();

        refreshTokenRepository.save(newTokenEntity);

        // 9️⃣ Create new access token
        String newAccessToken = jwtService.generateAccessToken(
                username,
                List.of("USER") 
        );

        return List.of(Map.of("access_token", newAccessToken), Map.of("refresh_token", newRefreshToken));
    }
}
