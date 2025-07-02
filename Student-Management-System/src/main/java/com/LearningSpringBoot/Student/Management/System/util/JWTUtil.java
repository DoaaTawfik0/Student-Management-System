package com.LearningSpringBoot.Student.Management.System.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JWTUtil {

    private final long EXPIRATION_TIME = 1000 * 60 * 60;
    private final String SECRET = "my-super-secret-key-that-is-long-enough-1234567890!@#";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, String username, UserDetails userDetails) {
        try {
            return isSameUsername(username, userDetails) && !isTokenExpired(token);
        } catch (ExpiredJwtException ex) {
            log.warn("Token expired: {}", ex.getMessage());
            return false;
        }
    }

    private boolean isSameUsername(String username, UserDetails userDetails) {
        return username.equals(userDetails.getUsername());
    }

    public boolean isTokenExpired(String token) throws ExpiredJwtException {
        return extractClaims(token).getExpiration().before(new Date());
    }
}