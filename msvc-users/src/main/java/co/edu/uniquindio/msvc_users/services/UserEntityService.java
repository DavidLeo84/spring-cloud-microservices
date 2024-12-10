package co.edu.uniquindio.msvc_users.services;

import co.edu.uniquindio.msvc_users.dtos.UpdateDTO;
import co.edu.uniquindio.msvc_users.dtos.UserEntityDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserEntityService {

    void saveUser(UserEntityDTO dto);

    UserEntityDTO updateUser(Long id, UpdateDTO dto) throws Throwable;

    UserEntityDTO getUserById(Long id);

    UserEntityDTO getUserByUsername(String email, boolean state);

    List<UserEntityDTO> getAllUsers();

    void deleteUser(Long id);


}
