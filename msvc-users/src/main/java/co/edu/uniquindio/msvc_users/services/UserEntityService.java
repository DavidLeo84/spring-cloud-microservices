package co.edu.uniquindio.msvc_users.services;

import co.edu.uniquindio.msvc_users.Dtos.UserEntityDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserEntityService {

    UserEntityDTO saveUser(UserEntityDTO dto);

    UserEntityDTO updateUser(UserEntityDTO dto);

    UserEntityDTO getUserById(Long id);

    UserEntityDTO getUserByUsername(String email, boolean state);

    List<UserEntityDTO> getAllUsers();

    void deleteUser(Long id);


}
