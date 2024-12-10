package co.edu.uniquindio.msvc_users.repositories;

import co.edu.uniquindio.msvc_users.entities.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);
}
