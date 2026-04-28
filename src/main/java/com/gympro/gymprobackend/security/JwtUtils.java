package com.gympro.gymprobackend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    // Read values from application.properties
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    // Build the signing key from our secret string
    private SecretKey key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // Called after login: create a token for the authenticated user
    public String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .subject(userPrincipal.getUsername())   // subject = email
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    // Extract email from a token (used by the filter)
    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Returns true if the token is valid and not expired
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(key()).build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Token is expired, malformed, or signature is wrong
            System.err.println("Invalid JWT: " + e.getMessage());
            return false;
        }
    }
}