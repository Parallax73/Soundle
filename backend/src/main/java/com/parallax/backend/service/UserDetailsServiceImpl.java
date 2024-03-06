package com.parallax.backend.service;

import com.parallax.backend.entity.MainUser;
import com.parallax.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final
    UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (repository.findByUserName(userName).isPresent()){
            var user = repository.findByUserName(userName).get();
            return MainUser.build(user);
        }
        return null;
    }
}
