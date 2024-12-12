package co.edu.uniquindio.msvc_oauth2.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


public class UserEntity {

    Long id;

    String username;

    String password;

    Boolean enabled;

    boolean admin;

    String email;

    List<RoleEntity> roles = new ArrayList<>();

}
