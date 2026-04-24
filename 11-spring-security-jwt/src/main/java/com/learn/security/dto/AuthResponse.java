package com.learn.security.dto;

/**
 * Response body for POST /auth/login
 * { "token": "eyJhbGci..." }
 */
public class AuthResponse {
    private String token;

    public AuthResponse(String token) { this.token = token; }
    public String getToken() { return token; }
}
