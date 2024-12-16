package co.edu.uniquindio.msvc_users.controllers;

import co.edu.uniquindio.msvc_users.dtos.MessageDTO;
import co.edu.uniquindio.msvc_users.dtos.UpdateDTO;
import co.edu.uniquindio.msvc_users.dtos.UserEntityDTO;
import co.edu.uniquindio.msvc_users.services.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
public class UserEntityController {

    private final UserEntityService userEntityService;

    public UserEntityController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @PostMapping
    public ResponseEntity<MessageDTO<String>> saveUser(@Valid @RequestBody UserEntityDTO dto) {
        try {
            userEntityService.saveUser(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageDTO<>(false, "User created"));
        }
        catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageDTO<>(true, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO<?>> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageDTO<>(false, userEntityService.updateUser(id, dto)));
        }
        catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageDTO<>(true, e.getMessage()));
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO<?>> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new MessageDTO<>(false, userEntityService.getUserById(id)));
        } catch (NoSuchElementException e) {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "User not found"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO<>(true, e.getMessage()));
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserEntityDTO> getUserByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userEntityService.getUserByUsername(username, true));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
    }
}
    @GetMapping
    public ResponseEntity<MessageDTO<?>> getAllUsers() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new MessageDTO<>(false, userEntityService.getAllUsers()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO<>(true, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<MessageDTO<?>> deleteUser(@Valid @PathVariable Long id) {
        try {
            userEntityService.deleteUser(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO<>(false,"User not found"));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageDTO<>(true,"User deleted"));
    }
}
