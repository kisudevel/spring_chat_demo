package com.sucoding.chatdemo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RefreshTokenCookie {

    public static final String NAME = "refreshToken";
    private static final String PATH = "/api/public/auth/refresh";
    private final long refreshExpirationMillis;

    public RefreshTokenCookie(@Value("${jwt.refresh-expiration}") long refreshExpirationMillis) {
        this.refreshExpirationMillis = refreshExpirationMillis;
    }

    private ResponseCookie.ResponseCookieBuilder base(String value) {
        return ResponseCookie.from(NAME, value)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path(PATH);
    }

    public ResponseCookie create(String token) {
        return base(token).maxAge(Duration.ofMillis(refreshExpirationMillis)).build();
    }

    public ResponseCookie expire() {
        return base("").maxAge(0).build();
    }
}
