package com.parallax.backend.service;

import com.parallax.backend.entity.User;
import com.parallax.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    final
    UserRepository userRepository;


    public UserService(UserRepository userRepository) {



        this.userRepository = userRepository;
    }

    public Optional<User> getByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public void save(User user){
        userRepository.save(user);
    }


}
