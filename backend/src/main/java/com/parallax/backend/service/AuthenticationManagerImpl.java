package com.parallax.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationManagerImpl {

    @Autowired
    AuthenticationManager authenticationManager;


    public Authentication authentication(String username, String password){
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password));
    }




}
