package com.piplop.play.web.exception;

import com.piplop.play.domain.exceptions.MovieAlreadyExistsException;
import com.piplop.play.domain.exceptions.MovieNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(MovieAlreadyExistsException.class)
    public ResponseEntity<Error> handleException(MovieAlreadyExistsException ex){
        Error error = new Error("movie-already-exists", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<Error> handleException(MovieNotFoundException ex){
        Error error = new Error("movie-not-found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Error>> handleException(MethodArgumentNotValidException ex){
        List<Error> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.add(new Error(error.getField(), error.getDefaultMessage()));
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception ex){
        Error error = new Error("unknown-error", ex.getMessage());
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handleException(HttpMessageNotReadableException ex){
        Error error = new Error("invalid-request-body","El body de la petición es inválido o está vacío");

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<Error>> handleException(ConstraintViolationException ex){
        List<Error> errors = new ArrayList<>();

        for (ConstraintViolation<?> error : ex.getConstraintViolations()) {
            errors.add(new Error(error.getPropertyPath().toString(), error.getMessage()));
        }

        return ResponseEntity.badRequest().body(errors);
    }
}
