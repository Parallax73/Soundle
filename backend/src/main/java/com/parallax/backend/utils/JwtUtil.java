package com.parallax.backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.api.secret.key}")
    private String SECRET;

    private final Key key;

    public JwtUtil(@Value("${jwt.api.secret.key}") String secret) {
        this.SECRET = secret;
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String username,ROLE role) {
        return Jwts.builder()
                .subject(username)
                .subject(role.toString())
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .issuedAt(new Date())
                .compact();

    }


    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

}
