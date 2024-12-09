package co.edu.uniquindio.msvc_users.controllers;

import co.edu.uniquindio.msvc_users.Dtos.UserEntityDTO;
import co.edu.uniquindio.msvc_users.services.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.NoSuchElementException;

@RestController
public class UserEntityController {

    private final UserEntityService userEntityService;

    public UserEntityController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserEntityDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userEntityService.saveUser(dto));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserEntityDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userEntityService.updateUser(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userEntityService.getUserById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "User not found"));
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@Valid @PathVariable String username) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userEntityService.getUserByUsername(username, true));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "User not found"));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userEntityService.getAllUsers());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Users not found"));
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteUser(@Valid @PathVariable Long id) {
        try {
            userEntityService.deleteUser(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "User not found"));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.singletonMap("message", "User eliminated"));
    }
}
