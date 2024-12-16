package co.edu.uniquindio.msvc_users.services;

import co.edu.uniquindio.msvc_users.dtos.RoleDTO;
import co.edu.uniquindio.msvc_users.dtos.UpdateDTO;
import co.edu.uniquindio.msvc_users.dtos.UserEntityDTO;
import co.edu.uniquindio.msvc_users.entities.RoleEntity;
import co.edu.uniquindio.msvc_users.entities.UserEntity;
import co.edu.uniquindio.msvc_users.repositories.RoleRepository;
import co.edu.uniquindio.msvc_users.repositories.UserEntityRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    private final UserEntityRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public UserEntityServiceImpl(UserEntityRepository repository, PasswordEncoder encoder,
                                 RoleRepository roleRepository) {
        this.repository = repository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void saveUser(UserEntityDTO dto) {
        List<RoleEntity> roles = new ArrayList<>();
        try {
            Optional<UserEntity> optional = repository.findByUsernameAndEnabled(dto.username(), true);
            if (optional.isPresent()) {
                throw new NoSuchElementException();
            }
            Optional<UserEntity> optional2 = repository.findByEmailAndEnabled(dto.email(), true);
            if (optional2.isPresent()) {
                throw new NoSuchElementException();
            }
            Optional<RoleEntity> optionalRole = roleRepository.findByName("ROLE_USER");
            optionalRole.ifPresent(roles::add);
            //optionalRole.ifPresent(role -> roles.add(role)); forma alternativa
            if (dto.admin())  {
                Optional<RoleEntity> roleOptional = roleRepository.findByName("ROLE_ADMIN");
                roleOptional.ifPresent(roles::add);
            }
            UserEntity userEntity = UserEntity.builder()
                    .username(dto.username())
                    .password(encoder.encode(dto.password()))
                    .email(dto.email())
                    .enabled(true)
                    .roles(roles)
                    .build();
            repository.save(userEntity);
        }
        catch (NoSuchElementException e){
            throw new NoSuchElementException("User already exists");
        }
    }

    @Override
    @Transactional
    public UserEntityDTO updateUser(Long id, UpdateDTO dto) throws Throwable {
        try {
            UserEntity userEntity = repository.findByIdAndEnabled(id, true).orElseThrow();
            if (userEntity.getEmail().equals(dto.email()) && userEntity.getUsername().equals(dto.username()) && !userEntity.isAdmin()) {
                throw new NoSuchElementException();
            }
            List<RoleEntity> roles = userEntity.getRoles();
            Optional<RoleEntity> roleOptional = roleRepository.findByName("ROLE_ADMIN");
            if (!dto.admin()) {
                roleOptional.ifPresent(roles::remove);
            } else{
                roleOptional.ifPresent(roles::add);
            }
            userEntity.setUsername(dto.username());
            userEntity.setEmail(dto.email());
            userEntity.setRoles(roles);

            repository.save(userEntity);
            return new UserEntityDTO(
                    String.valueOf(userEntity.getId()),
                    userEntity.getUsername(),
                    userEntity.getPassword(),
                    userEntity.getEmail(),
                    userEntity.isAdmin(),
                    userEntity.getRoles()
                            .stream()
                            .map(role -> new RoleDTO(
                                    role.getId(),
                                    role.getName()
                            )).toList());
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("User not found");
        }
        catch (Throwable e) {
            throw new Throwable("User already exists");
        }


    }

    @Override
    @Transactional(readOnly = true)
    public UserEntityDTO getUserById(Long id) {
        try {
            UserEntity userEntity = repository.findByIdAndEnabled(id, true).orElseThrow();
            return new UserEntityDTO(
                    String.valueOf(userEntity.getId()),
                    userEntity.getUsername(),
                    userEntity.getPassword(),
                    userEntity.getEmail(),
                    userEntity.isAdmin(),
                    userEntity.getRoles()
                            .stream()
                            .map(role -> new RoleDTO(
                                    role.getId(),
                                    role.getName()
                            )).toList()
            );
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("User not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntityDTO getUserByUsername(String email, boolean state) {
        Optional<UserEntity> optional = repository.findByUsernameAndEnabled(email, true);
        if (optional.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        UserEntity userEntity = optional.get();
        return new UserEntityDTO(
                String.valueOf(userEntity.getId()),
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.isAdmin(),
                userEntity.getRoles()
                        .stream()
                        .map(role -> new RoleDTO(
                                role.getId(),
                                role.getName()
                        )).toList()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntityDTO> getAllUsers() {
        List<UserEntity> list = repository.findAllByEnabled(true);
        if (list.isEmpty()) {
            throw new NoSuchElementException("Users not found");
        }
        return list.stream().map(user -> new UserEntityDTO(
                String.valueOf(user.getId()),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.isAdmin(),
                user.getRoles().stream().map(role -> new RoleDTO(
                        role.getId(),
                        role.getName()
                )).toList()
        )).toList();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        try {
            UserEntity userEntity = repository.findByIdAndEnabled(id, true).orElseThrow();
            userEntity.setEnabled(false);
            repository.save(userEntity);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("User not found");
        }
    }
}
