package com.parallax.backend.dto;

public record UserRegister(
        String username,
        String email,
        String password
) {
}
