package com.parallax.backend.service;

import com.parallax.backend.dto.Register;
import com.parallax.backend.entity.User;
import com.parallax.backend.repository.RoleRepository;
import com.parallax.backend.repository.UserRepository;
import com.parallax.backend.utils.JwtProvider;
import com.parallax.backend.utils.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class AuthService {


    final
    AuthenticationManagerImpl authenticationManager;
    final
    PasswordEncoder passwordEncoder;
    final
    UserService userService;
    final
    RoleRepository roleRepository;
    final
    JwtProvider jwtProvider;
    final
    UserRepository userRepository;


    public AuthService(AuthenticationManagerImpl authenticationManager, PasswordEncoder passwordEncoder, UserService userService, RoleRepository roleRepository, JwtProvider jwtProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }


    public ResponseEntity<Object> registerNewUserAccount(Register register)  {

        User user = new User();

        user.setUserName(register.username());
        user.setPassword(passwordEncoder.encode(register.password()));
        user.setEmail(register.email());

        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        userRepository.save(user);
        return  new ResponseEntity<>(new Message("Registration complete, new session started"), HttpStatus.CREATED);
    }

    public ResponseEntity<Message> getLoggedUsername(String token) {
        String username = jwtProvider.getUserNameFromToken(token);
        return new ResponseEntity<>(new Message(username),HttpStatus.OK);
    }

}
