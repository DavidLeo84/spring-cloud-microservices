package co.edu.uniquindio.msvc_users.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserEntityDTO(

        @NotBlank
        Long id,

        @NotBlank
        String username,

        @NotBlank
        String password,

        @Email
        @NotBlank
        String email
) {
}
