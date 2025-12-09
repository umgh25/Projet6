package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.RegisterRequestDto;
import com.openclassrooms.mddapi.exception.UserAlreadyRegisteredException;
import com.openclassrooms.mddapi.model.User;

public interface UserService {

    User addUser(RegisterRequestDto registerRequest) throws UserAlreadyRegisteredException;

}