package com.parallax.backend.utils;

import com.parallax.backend.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Value("${jwt.accessTokenCookieName}")
    private String cookieName;
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getToken(req);
            if (token != null && jwtProvider.validateToken(token)) {
                String userName = jwtProvider.getUserNameFromToken(token);
                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);

            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        filterChain.doFilter(req, res);
    }


    private String getToken(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request, cookieName);
        return cookie != null ? cookie.getValue():null;
    }

}
