package com.parallax.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record NewUser(

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9]{3,15}$\n")
        String username,
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "^{3,30}")
        String password
) {
}
