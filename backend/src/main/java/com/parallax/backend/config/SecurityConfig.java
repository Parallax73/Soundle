package com.parallax.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.GET).permitAll()
                        .requestMatchers(HttpMethod.POST).permitAll());
        return http.build();
    }


    @Bean
    BCryptPasswordEncoder bCryptEncoder(){
        return new BCryptPasswordEncoder();
    }


}
