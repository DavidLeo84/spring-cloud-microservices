package co.edu.uniquindio.msvc_users.services;

import co.edu.uniquindio.msvc_users.Dtos.UserEntityDTO;
import co.edu.uniquindio.msvc_users.entities.UsersEntity;
import co.edu.uniquindio.msvc_users.repositories.UserEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    private final UserEntityRepository repository;

    public UserEntityServiceImpl(UserEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public UserEntityDTO saveUser(UserEntityDTO dto) {

        UsersEntity usersEntity = UsersEntity.builder()
                .username(dto.username())
                .password(dto.password())
                .email(dto.email())
                .enabled(true)
                .build();
        repository.save(usersEntity);
        return new UserEntityDTO(
                usersEntity.getId(),
                usersEntity.getUsername(),
                usersEntity.getPassword(),
                usersEntity.getEmail());
    }

    @Override
    @Transactional
    public UserEntityDTO updateUser(UserEntityDTO dto) {
        try {
            UsersEntity usersEntity = repository.findById(dto.id()).orElseThrow();
            usersEntity.setUsername(dto.username());
            usersEntity.setPassword(dto.password());
            usersEntity.setEmail(dto.email());
            repository.save(usersEntity);
            return new UserEntityDTO(
                    usersEntity.getId(),
                    usersEntity.getUsername(),
                    usersEntity.getPassword(),
                    usersEntity.getEmail());
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("User not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntityDTO getUserById(Long id) {
        try {
            UsersEntity usersEntity = repository.findByIdAndEnabled(id, true).orElseThrow();
            return new UserEntityDTO(
                    usersEntity.getId(),
                    usersEntity.getUsername(),
                    usersEntity.getPassword(),
                    usersEntity.getEmail());
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("User not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntityDTO getUserByUsername(String email, boolean state) {
            Optional<UsersEntity> optional = repository.findByUsernameAndEnabled(email, true);
            if (optional.isEmpty()) {
                throw new NoSuchElementException("User not found");
            }
            UsersEntity usersEntity = optional.get();
            return new UserEntityDTO(
                    usersEntity.getId(),
                    usersEntity.getUsername(),
                    usersEntity.getPassword(),
                    usersEntity.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntityDTO> getAllUsers() {
        List<UsersEntity> list = repository.findAllByEnabled(true);
        if (list.isEmpty()) {
            throw new NoSuchElementException("Users not found");
        }
        return list.stream().map(user -> new UserEntityDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail()
        )).toList();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        try {
            UsersEntity usersEntity = repository.findByIdAndEnabled(id, true).orElseThrow();
            usersEntity.setEnabled(false);
            repository.save(usersEntity);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("User not found");
        }
    }
}
