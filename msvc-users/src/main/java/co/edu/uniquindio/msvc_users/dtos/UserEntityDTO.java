package co.edu.uniquindio.msvc_users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UserEntityDTO(

        String id,

        @NotBlank
        String username,

        @NotBlank
        String password,

        @Email
        @NotBlank
        String email,

        boolean admin,

        List<RoleDTO> roles
) {
}
