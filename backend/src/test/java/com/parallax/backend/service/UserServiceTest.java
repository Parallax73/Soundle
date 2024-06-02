package com.parallax.backend.service;

import com.parallax.backend.dto.UserLogin;
import com.parallax.backend.dto.UserRegister;
import com.parallax.backend.entity.User;
import com.parallax.backend.repository.UserRepository;
import com.parallax.backend.utils.JwtProvider;
import com.parallax.backend.utils.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testRegister_Success() {
        UserRegister register = new UserRegister("username", "email@example.com", "password");

        when(userRepository.existsByUsername(register.username())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(new User(register.username(), register.email(), encoder.encode(register.password())));

        ResponseEntity<Object> responseEntity = userService.register(register);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("User registered successfully", ((Message) Objects.requireNonNull(responseEntity.getBody())).getInfoMessage());
    }

    @Test
    public void testRegister_UsernameAlreadyExists() {
        UserRegister register = new UserRegister("username", "email@example.com", "password");

        when(userRepository.existsByUsername(register.username())).thenReturn(true);

        ResponseEntity<Object> responseEntity = userService.register(register);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Username already registered", ((Message) Objects.requireNonNull(responseEntity.getBody())).getInfoMessage());
    }

    @Test
    public void testLogin_Success() {
        UserLogin login = new UserLogin("username", "password");

        User user = new User(login.username(), "email@example.com", encoder.encode(login.password()));

        when(userRepository.findUserByUsername(login.username())).thenReturn(Optional.of(user));
        when(jwtProvider.generateToken(user)).thenReturn("token");

        doNothing().when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        ResponseEntity<Object> responseEntity = userService.login(login);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("token", ((Message) Objects.requireNonNull(responseEntity.getBody())).getInfoMessage());
    }

    @Test
    public void testLogin_Failure() {
        UserLogin login = new UserLogin("username", "wrongpassword");

        when(userRepository.findUserByUsername(login.username())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> userService.authenticate(login));
    }

    @Test
    public void testAuthenticate_Success() {
        UserLogin login = new UserLogin("username", "password");

        User user = new User(login.username(), "email@example.com", encoder.encode(login.password()));

        when(userRepository.findUserByUsername(login.username())).thenReturn(Optional.of(user));

        doNothing().when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        User authenticatedUser = userService.authenticate(login);

        assertEquals(user, authenticatedUser);
    }

    @Test
    public void testAuthenticate_Failure() {
        UserLogin login = new UserLogin("username", "wrongpassword");

        when(userRepository.findUserByUsername(login.username())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> userService.authenticate(login));
    }

    @Test
    public void testGetUsername() {
        String token = "token";
        when(jwtProvider.extractUsername(token)).thenReturn("username");

        String username = userService.getUsername(token);

        assertEquals("username", username);
    }
}
