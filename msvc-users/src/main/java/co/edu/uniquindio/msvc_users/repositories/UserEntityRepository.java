package co.edu.uniquindio.msvc_users.repositories;

import co.edu.uniquindio.msvc_users.entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UsersEntity, Long> {

    Optional<UsersEntity> findByUsernameAndEnabled(String username, boolean state);
    List<UsersEntity> findAllByEnabled(boolean state);
    Optional<UsersEntity> findByIdAndEnabled(Long id, boolean state);
}
