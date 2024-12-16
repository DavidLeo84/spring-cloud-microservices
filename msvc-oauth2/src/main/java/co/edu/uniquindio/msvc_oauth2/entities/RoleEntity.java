package co.edu.uniquindio.msvc_oauth2.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleEntity implements Serializable {

    Long id;

    String name;
}
