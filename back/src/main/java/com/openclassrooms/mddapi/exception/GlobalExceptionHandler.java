package com.openclassrooms.mddapi.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 * Handles exceptions and returns appropriate HTTP responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResourceNotFoundException.
     *
     * @param ex The thrown exception
     * @return ResponseEntity with a 404 Not Found status
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>("resource not found", HttpStatus.NOT_FOUND);
    }


    /**
     * Handles UserAlreadyRegisteredException.
     *
     * @param ex The thrown exception
     * @return ResponseEntity with a 409 Conflict status
     */
    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<String> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex) {
        return new ResponseEntity<>("user already registered", HttpStatus.CONFLICT);
    }

    /**
     * Handles BadRequestException.
     *
     * @param ex The thrown exception
     * @return ResponseEntity with a 400 Bad Request status and the exception message
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
        String message = ex.getMessage() != null && !ex.getMessage().isEmpty() 
            ? ex.getMessage() 
            : "bad request";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

}