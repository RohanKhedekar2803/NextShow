package com.example.booking_service.Security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtContext {
    private static final ThreadLocal<UsernamePasswordAuthenticationToken> jwtHolder = new ThreadLocal<>();

    public static void setToken(UsernamePasswordAuthenticationToken token) {
        jwtHolder.set(token);
    }

    public static UsernamePasswordAuthenticationToken getToken() {
        return jwtHolder.get();
    }

    public static void clear() {
        jwtHolder.remove();
    }
}
