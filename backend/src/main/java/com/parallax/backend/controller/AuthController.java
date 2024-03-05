package com.parallax.backend.controller;

import com.parallax.backend.dto.LoginUser;
import com.parallax.backend.dto.NewUser;
import com.parallax.backend.entity.Message;
import com.parallax.backend.entity.Role;
import com.parallax.backend.entity.User;
import com.parallax.backend.enums.RoleList;
import com.parallax.backend.service.RoleService;
import com.parallax.backend.service.UserService;
import com.parallax.backend.utils.CookieUtil;
import com.parallax.backend.utils.JwtProvider;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final JwtProvider jwtProvider;

    @Value("${jwt.accessTokenCookieName}")
    private String cookieName;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                          UserService userService, RoleService roleService, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(HttpServletResponse httpServletResponse,
                                        @Valid @RequestBody LoginUser loginUser, BindingResult bidBindingResult){
        if(bidBindingResult.hasErrors())
            return new ResponseEntity<>(new Message("Revise sus credenciales"), HttpStatus.BAD_REQUEST);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.username(), loginUser.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            CookieUtil.create(httpServletResponse, cookieName, jwt, false, -1, "localhost");
            return new ResponseEntity<>(new Message("Sesión iniciada"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Revise sus credenciales"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) throws RoleNotFoundException {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(new Message("Revise los campos e intente nuevamente"), HttpStatus.BAD_REQUEST);
        User user = new User(newUser.username(), newUser.email(),
                passwordEncoder.encode(newUser.password()));

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRoleOptional = roleService.getByRoleName(RoleList.ROLE_USER);
        if (userRoleOptional.isPresent()) {
            roles.add(userRoleOptional.get());
        } else {
            throw new RoleNotFoundException("Role ROLE_USER not found");
        }
        roles.add(roleService.getByRoleName(RoleList.ROLE_USER).get());
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(new Message("Registro exitoso! Inicie sesión"), HttpStatus.CREATED);
    }


    @GetMapping("/details")
    public ResponseEntity<Object> getUserDetails(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName = userDetails.getUsername();
        Optional<User> user= this.userService.getByUserName(userName);
        if (!user.isPresent())
            return new ResponseEntity<>(new Message("No encotrado"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user.get(), HttpStatus.OK) ;
    }


    @GetMapping("/logout")
    public ResponseEntity<Message> logOut(HttpServletResponse httpServletResponse){
        CookieUtil.clear(httpServletResponse,cookieName);
        return new ResponseEntity<>(new Message("Sesión cerrada"), HttpStatus.OK) ;
    }

}
