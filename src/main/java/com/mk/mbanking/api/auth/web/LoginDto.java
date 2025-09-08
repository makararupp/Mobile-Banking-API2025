package com.mk.mbanking.api.auth.web;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank
        @Email
        @Schema(description = "Email address from user")
        String email,
        @NotBlank
        String password
                       ) {
}
