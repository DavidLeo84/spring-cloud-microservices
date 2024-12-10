package co.edu.uniquindio.msvc_users.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoleDTO(

        @NotNull
        Long id,
        @NotBlank
        String name
) {
}
