package com.learn.security.dto;

/**
 * Request body for POST /auth/login
 * { "username": "admin", "password": "password" }
 */
public class AuthRequest {
    private String username;
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
