package com.parallax.backend.controller;


import com.parallax.backend.entity.User;
import com.parallax.backend.entity.UserDTO;
import com.parallax.backend.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/v1/users")
public class UserController {

    final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/new-user")
    @Transactional
    public void createTest(@RequestBody UserDTO userDTO){
        userService.createUser(userDTO);
    }

    @PostMapping("/login-user")
    public void loginUser(@RequestBody UserDTO dto, HttpServletResponse response){
        Cookie cookie = new Cookie("MyCookieName", "tatata");
        response.addCookie(cookie);
        userService.loginUser(dto,response);
    }

    @GetMapping("/get-cookie")
    public void teste(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            log.info(Arrays.stream(cookies)
                    .map(c -> c.getName() + "=" + c.getValue()).collect(Collectors.joining(", ")));
        }
    }

    @GetMapping("/rank/score")
    public List<User> rankingByScore(){
        return userService.scoreRanking();
    }

    @GetMapping("/rank/streak")
    public List<User> rankingByStreak(){
        return userService.streakRanking();
    }

    /*@PutMapping()
    public void setScore(@RequestBody ){
        userService.addScore();
    }*/
}
