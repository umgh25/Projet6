package com.openclassrooms.mddapi.exception;

public class UserAlreadyRegisteredException extends RuntimeException {

    public UserAlreadyRegisteredException() {
        super(message);
    }
}