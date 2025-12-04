package com.openclassrooms.mddapi.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<String> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex) {
        return new ResponseEntity<>("user already registered", HttpStatus.BAD_REQUEST);
    }

}