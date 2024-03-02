package com.parallax.backend.entity;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
