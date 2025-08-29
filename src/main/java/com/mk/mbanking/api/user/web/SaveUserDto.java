package com.mk.mbanking.api.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SaveUserDto(
        @NotBlank(message = "name must be required..!")
        @Size(min = 5, max = 10)
        String name,
        @NotBlank
        String gender,
        @NotNull
        Boolean isStudent,
        String studentCardId
) {
}
