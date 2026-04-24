package com.learn.security.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * ============================================================
 *  JWT UTILITY  —  generate, validate, and parse JWT tokens
 * ============================================================
 *
 *  A JWT has three Base64-encoded parts separated by dots:
 *
 *    HEADER.PAYLOAD.SIGNATURE
 *
 *  Header  : algorithm used (HS256)
 *  Payload : claims — username, issued-at, expiry
 *  Signature: HMAC-SHA256(header + payload, secret key)
 *
 *  We use the JJWT library (io.jsonwebtoken) to handle this.
 * ============================================================
 */
@Component
public class JwtUtil {

    // Secret key loaded from application.properties
    @Value("${jwt.secret}")
    private String secret;

    // Token validity: 24 hours in milliseconds
    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    // Build the signing key from the secret string
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Generate a JWT token for the given username.
     * Sets: subject (username), issuedAt (now), expiration (now + 24h)
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extract the username (subject) from a token.
     */
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * Validate: checks signature + expiry.
     * Returns true only if the token is valid AND belongs to this user.
     */
    public boolean validateToken(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);
            return extractedUsername.equals(username) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
