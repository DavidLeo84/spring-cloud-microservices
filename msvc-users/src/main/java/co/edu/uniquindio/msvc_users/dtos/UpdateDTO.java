package co.edu.uniquindio.msvc_users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateDTO(

        @NotBlank
        String username,

        @Email
        @NotBlank
        String email,

        boolean admin
) {
}
