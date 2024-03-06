package com.parallax.backend.utils;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtProvider {


    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;


    public String generateToken(Authentication authentication){

        UserDetails mainUser = (UserDetails) authentication.getPrincipal();
        log.info(mainUser.getUsername());
        return Jwts.builder().setSubject(mainUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000L))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUserNameFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }




    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e){
            log.error("Token wasn't built correctly");
        }catch (UnsupportedJwtException e){
            log.error("Token isn't supported");
        }catch (ExpiredJwtException e){
            log.error("Expired token");
        }catch (IllegalArgumentException e){
            log.error("Empty token");
        }catch (SignatureException e){
            log.error("Token failed sign");
        }
        return false;
    }
}