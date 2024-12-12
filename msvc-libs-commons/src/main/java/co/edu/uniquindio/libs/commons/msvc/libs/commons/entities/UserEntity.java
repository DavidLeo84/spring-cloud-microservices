package co.edu.uniquindio.libs.commons.msvc.libs.commons.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    @Column(name = "user_name", unique = true, nullable = false)
    String username;

    @NotBlank
    String password;

    Boolean enabled;

    @Transient
    boolean admin;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    String email;

    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})})
    List<RoleEntity> roles = new ArrayList<>();

    @Builder
    public UserEntity(String username, String password, Boolean enabled, String email, boolean admin,
                      List<RoleEntity> roles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.email = email;
        this.admin = admin;
        this.roles = roles;
    }
}
