package com.parallax.backend.service;

import com.parallax.backend.dto.UserLogin;
import com.parallax.backend.dto.UserRegister;
import com.parallax.backend.entity.User;
import com.parallax.backend.enums.Role;
import com.parallax.backend.exception.ApiException;
import com.parallax.backend.repository.UserRepository;
import com.parallax.backend.utils.JwtProvider;
import com.parallax.backend.utils.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    JwtProvider provider;

    @Autowired
    AuthenticationManager authenticationManager;


    final static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public ResponseEntity<Object> register(UserRegister register){
        if (repository.existsByUsername(register.username()))
                return new ResponseEntity<>(new Message("Username already registered"), HttpStatus.BAD_REQUEST);

        try{
            String passwordEncoder = encoder.encode(register.password());
            User user = new User(register.username(), register.email(), passwordEncoder);
            user.setRole(Role.USER);
            repository.save(user);
            return new ResponseEntity<>(new Message("User registered successfully"), HttpStatus.CREATED);
        } catch (ApiException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> login (UserLogin login){
        try {
            User authenticatedUser = authenticate(login);

            String token = provider.generateToken(authenticatedUser);
            return new ResponseEntity<>(new Message(token),HttpStatus.OK);
        } catch (ApiException e){
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public User authenticate(UserLogin login) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.username(),
                        login.password()
                )
        );

        return repository.findUserByUsername(login.username())
                .orElseThrow();
    }

}
