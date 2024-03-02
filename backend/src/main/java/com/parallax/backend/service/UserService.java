package com.parallax.backend.service;



import com.parallax.backend.entity.User;
import com.parallax.backend.entity.UserDTO;
import com.parallax.backend.entity.newPasswordDTO;
import com.parallax.backend.exception.ApiException;
import com.parallax.backend.repository.UserRepository;
import com.parallax.backend.utils.JwtUtil;
import com.parallax.backend.utils.ROLE;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    final
    UserRepository repository;

    final
    SpotifyService service;

    final
    JwtUtil jwtUtil;

    public UserService(UserRepository repository, SpotifyService service, JwtUtil jwtUtil) {
        this.repository = repository;
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    public void createUser(UserDTO userDTO){
        try{
            var user = new User(userDTO);
            repository.save(user);
        } catch (ApiException e){
            throw new ApiException("Error while creating user");
        }
    }

    public void changePassword(newPasswordDTO dto){
        try {
            var user = repository.findByUsername(dto.username());
            if (dto.password().equals(user.getPassword())){
                user.changePassword(dto.newPassword());
            }
        } catch (ApiException e){
            log.info(e.getMessage());
        }
    }

    public void loginUser(UserDTO dto, HttpServletResponse response){
        try{
            var user = repository.findByUsername(dto.username());
            if (user.decodePassword(dto.password())){
                log.info(" {} was logged",dto.username());

                Cookie cookie = new Cookie("JWToken", jwtUtil.generateToken(dto.username(), ROLE.USER));
                response.addCookie(cookie);
            }
        } catch (ApiException e){
        throw new ApiException("Bad credentials");
        }
    }

    public void addScore(Integer score,String username){
        try{
            var user = repository.findByUsername(username);
            if (user!=null){
                user.addScore(score);
            }
        } catch (ApiException e){
            throw new ApiException("Bad credentials");
        }
    }

    public void addStreak(Integer streak,String username){
        try{
            var user = repository.findByUsername(username);
            if (user!=null){
                user.addStreak(streak);
            }
        } catch (ApiException e){
            throw new ApiException("Bad credentials");
        }
    }

    public List<User> scoreRanking(){
        return repository.rankingByScore();
    }

    public List<User> streakRanking(){
        return repository.rankingByStreak();
    }

    public void setId(String userUrl, String username){
        try {
            var user = repository.findByUsername(username);
            if (user!=null){
                if (service.isIdValid(userUrl)){
                    var index = userUrl.lastIndexOf("/");
                    String userId = userUrl.substring(index);
                    user.setSpotifyID(userId);
                }
            }
        } catch (ApiException e){
            throw new ApiException("Bad credentials");
        }
    }



}
