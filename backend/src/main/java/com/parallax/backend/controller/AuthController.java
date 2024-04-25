package com.parallax.backend.controller;

import com.parallax.backend.dto.UserLogin;
import com.parallax.backend.dto.UserRegister;
import com.parallax.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("api/v1/users")
public class AuthController {

    @Autowired
    UserService service;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody UserRegister register){
       return service.register(register);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login (@Valid @RequestBody UserLogin login){
        return service.login(login);
    }

    @GetMapping("/username/{token}")
    public ResponseEntity<Object> username(@PathVariable("token") String token){
        return ResponseEntity.ok().body(service.getUsername(token));
    }

}
