package co.edu.uniquindio.msvc_users.controllers.exceptions;

import co.edu.uniquindio.msvc_users.dtos.MessageDTO;
import co.edu.uniquindio.msvc_users.dtos.ValidationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageDTO<String>> generalException(Exception e) {
        return ResponseEntity.internalServerError().body(new MessageDTO<>(true, e.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageDTO<List<ValidationDTO>>> validationException(
            MethodArgumentNotValidException ex) {
        List<ValidationDTO> errors = new ArrayList<>();
        BindingResult results = ex.getBindingResult();
        for (FieldError e : results.getFieldErrors()) {
            errors.add(new ValidationDTO(e.getField(), e.getDefaultMessage()));
        }
        return ResponseEntity.badRequest().body(new MessageDTO<>(true, errors));
    }
}