package com.parallax.backend.service;

import com.parallax.backend.dto.LoginUser;
import com.parallax.backend.dto.NewUser;
import com.parallax.backend.entity.Message;
import com.parallax.backend.entity.Role;
import com.parallax.backend.entity.User;
import com.parallax.backend.enums.RoleList;
import com.parallax.backend.repository.UserRepository;
import com.parallax.backend.utils.CookieUtil;
import com.parallax.backend.utils.JwtProvider;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.management.relation.RoleNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
    RoleService roleService;
    final
    JwtProvider jwtProvider;
    final
    UserRepository userRepository;

    @Value("${jwt.accessTokenCookieName}")
    private String cookieName;

    public AuthService(AuthenticationManagerImpl authenticationManager, PasswordEncoder passwordEncoder, UserService userService, RoleService roleService, JwtProvider jwtProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }


    public ResponseEntity<Object> login(HttpServletResponse httpServletResponse,
                                        @Valid @RequestBody LoginUser loginUser, BindingResult bidBindingResult){
        if(bidBindingResult.hasErrors())
            return new ResponseEntity<>(new Message("Wrong credentials"), HttpStatus.BAD_REQUEST);
        try {
            var authentication = authenticationManager.authentication(loginUser.username(), loginUser.password());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            CookieUtil.create(httpServletResponse, cookieName, jwt, false, -1, "localhost");
            log.info(jwt);
            return new ResponseEntity<>(new Message(jwt), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Wrong credentials"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> registerUser(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) throws RoleNotFoundException {
        log.info("register method called");
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(new Message("Something went wrong, check your fields" + bindingResult.toString()), HttpStatus.BAD_REQUEST);
        User user = new User(newUser.username(), newUser.email(),
                passwordEncoder.encode(newUser.password()));

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRoleOptional = roleService.getByRoleName(RoleList.ROLE_USER);
        if (userRoleOptional.isPresent()) {
            roles.add(userRoleOptional.get());
        } else {
            throw new RoleNotFoundException("Role not found");
        }
        if(roleService.getByRoleName(RoleList.ROLE_USER).isPresent())
            roles.add(roleService.getByRoleName(RoleList.ROLE_USER).get());
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(new Message("Registration complete, new session started"), HttpStatus.CREATED);
    }

    public ResponseEntity<Message> logoutUser(HttpServletResponse httpServletResponse){
        CookieUtil.clear(httpServletResponse,cookieName);
        return new ResponseEntity<>(new Message("Session was finished"), HttpStatus.OK) ;
    }


    public ResponseEntity<Object> getUserDetails(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName = userDetails.getUsername();
        Optional<User> user= this.userService.getByUserName(userName);
        return user.<ResponseEntity<Object>>map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new Message("User was not found"), HttpStatus.NOT_FOUND));
    }


    public ResponseEntity<?> addScore(int score){
        var username = getUserDetails().getBody();
        if (username==null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        var user = userRepository.findByUserName(username.toString());
        user.ifPresent(value -> value.addScore(score));
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    public ResponseEntity<?> addStreak(int streak){
        var username = getUserDetails().getBody();
        if (username==null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        var user = userRepository.findByUserName(username.toString());
        user.ifPresent(value -> value.addStreak(streak));
        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
