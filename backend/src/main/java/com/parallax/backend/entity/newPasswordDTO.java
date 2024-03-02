package com.parallax.backend.entity;

import jakarta.validation.constraints.NotBlank;

public record newPasswordDTO(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        String newPassword
) {
}
