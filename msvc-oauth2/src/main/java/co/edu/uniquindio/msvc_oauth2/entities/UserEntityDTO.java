package co.edu.uniquindio.msvc_oauth2.entities;

import java.util.List;

public record UserEntityDTO(

        Long id,

        String username,

        String password,

        String email,

        boolean admin,

        List<RoleEntity> roles

) {
}
