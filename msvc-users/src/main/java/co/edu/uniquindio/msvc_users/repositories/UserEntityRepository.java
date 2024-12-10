package co.edu.uniquindio.msvc_users.repositories;

import co.edu.uniquindio.msvc_users.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsernameAndEnabled(String username, boolean state);

    List<UserEntity> findAllByEnabled(boolean state);

    Optional<UserEntity> findByIdAndEnabled(Long id, boolean state);

    Optional<UserEntity> findByEmailAndEnabled(String username, boolean state);
}
