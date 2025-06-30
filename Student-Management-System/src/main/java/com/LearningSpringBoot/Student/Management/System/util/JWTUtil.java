package com.LearningSpringBoot.Student.Management.System.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    private final long EXPIRATION_DATE = 1000 * 60 * 60;
    private final String SECRET = "My-string-secret-Key-least-256-bits-long-12345678#757";

    Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateJwtToken(String username) {
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
