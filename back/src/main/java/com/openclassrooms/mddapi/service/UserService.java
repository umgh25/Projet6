package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.RegisterRequestDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.exception.UserAlreadyRegisteredException;
import com.openclassrooms.mddapi.model.User;

import java.util.Optional;

/**
 * Service interface for user management.
 */
public interface UserService {

    /**
     * Registers a new user.
     *
     * @param registerRequest Registration data
     * @return The created user
     * @throws UserAlreadyRegisteredException If the email or username is already in use
     */
    User addUser(RegisterRequestDto registerRequest) throws UserAlreadyRegisteredException;

    /**
     * Updates an existing user.
     *
     * @param user User to update
     * @return The updated user
     */
    User updateUser(User user);

    /**
     * Updates the current user's information.
     *
     * @param user User data to update
     * @return The updated user
     * @throws UserAlreadyRegisteredException If the email or username is already in use
     * @throws ResourceNotFoundException If the user is not found
     */
    User updateUser(UserDto user) throws UserAlreadyRegisteredException, ResourceNotFoundException;

    /**
     * Finds a user by email address.
     *
     * @param email User's email address
     * @return Optional containing the user if found
     */
    Optional<User> findUserByMail(String email);

    /**
     * Gets the currently authenticated user.
     *
     * @return The authenticated user
     * @throws ResourceNotFoundException If the user is not found
     */
    User getLoggedUser() throws ResourceNotFoundException;

    /**
     * Finds the currently authenticated user and returns it as a DTO.
     *
     * @return UserDto containing the user's information
     * @throws ResourceNotFoundException If the user is not found
     */
    UserDto findUser()  throws ResourceNotFoundException;

    /**
     * Checks if an email address is already taken.
     *
     * @param userMail Email address to check
     * @return true if the email is taken, false otherwise
     */
    Boolean isEmailAlreadyTaken(String userMail);

    /**
     * Checks if a username is already taken.
     *
     * @param userName Username to check
     * @return true if the username is taken, false otherwise
     */
    Boolean isUserNameAlreadyTaken(String userName);

}