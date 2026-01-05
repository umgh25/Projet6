package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.RegisterRequestDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.exception.UserAlreadyRegisteredException;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    /**
     * Register a new user
     *
     * @param registerRequest
     * @return the registered user
     * @throws UserAlreadyRegisteredException if the email or username is already taken
     */
    @Override
    public User addUser(RegisterRequestDto registerRequest) throws UserAlreadyRegisteredException {
        log.info("Check if user is already registered");
        this.isUserAlreadyRegister(registerRequest.getEmail(), registerRequest.getUserName());

        User userToSave = User.builder()
                .userName(registerRequest.getUserName())
                .email(registerRequest.getEmail())
                .password(this.passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        log.info("Save user {} in database", userToSave.getEmail());
        return this.userRepository.save(userToSave);
    }

    /**
     * Update user
     *
     * @param user
     * @return user updated
     */
    @Override
    public User updateUser(User user) {
        return this.userRepository.save(user);
    }

    /**
     * Updates the currently authenticated user's information based on the provided DTO
     *
     * @param user
     * @return user updated
     * @throws UserAlreadyRegisteredException if email or username is already taken
     * @throws ResourceNotFoundException if user not found
     */
    @Override
    public User updateUser(UserDto user) throws UserAlreadyRegisteredException, ResourceNotFoundException {
        User userToUpdate = this.getLoggedUser();
        User userUpdated = this.updateUser(userToUpdate, user);
        return this.userRepository.save(userUpdated);
    }

    /**
     * Find a user by email
     *
     * @param email
     * @return an optional containing the user if found
     */
    @Override
    public Optional<User> findUserByMail(String email) {
        return this.userRepository.findByEmail(email);
    }

    /**
     * Retrieve the logged user
     *
     * @return the logged user
     * @throws ResourceNotFoundException if user not found
     */
    @Override
    public User getLoggedUser() throws ResourceNotFoundException {
        return this.findUserByMail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * Retrieve the logged user
     *
     * @return the logged user as Dto
     * @throws ResourceNotFoundException if user not found
     */
    @Override
    public UserDto findUser() throws ResourceNotFoundException {
        User user = this.getLoggedUser();
        return this.userMapper.asUserDto(user);
    }

    /**
     * Check if an email address is already taken
     *
     * @param userMail
     * @return true if the email address is already taken, otherwise false
     */
    @Override
    public Boolean isEmailAlreadyTaken(String userMail) {
        return this.findUserByMail(userMail).isPresent();
    }

    /**
     * Check if a username is already taken
     *
     * @param userName
     * @return true if the username is already taken, otherwise false
     */
    @Override
    public Boolean isUserNameAlreadyTaken(String userName) {
        return this.userRepository.findByUserName(userName).isPresent();
    }

    /**
     * Check if user is already registered
     *
     * @param email user's email
     * @param username username
     * @throws UserAlreadyRegisteredException if user is already registered
     */
    private void isUserAlreadyRegister(String email, String username) throws UserAlreadyRegisteredException {
        Optional<User> user = this.userRepository.findByEmail(email);
        user = user.isPresent() ? user : userRepository.findByUserName(username);
        if (user.isPresent()) {
            log.error("User is already registered");
            throw new UserAlreadyRegisteredException();
        }
    }

    /**
     * Update user information
     *
     * @param existitingUser the existing user in the database
     * @param user the user with the new data
     * @return the updated user
     * @throws UserAlreadyRegisteredException if the username or email already exists in the database
     */
    private User updateUser(User existitingUser, UserDto user) throws UserAlreadyRegisteredException {
        if (!existitingUser.getUserName().equals(user.getUserName())) {
            log.info("Username has changed, update user with username {}", user.getUserName());
            if (isUserNameAlreadyTaken(user.getUserName())) {
                throw new UserAlreadyRegisteredException();
            }
            existitingUser.setUserName((user.getUserName()));
        }

        if (!existitingUser.getEmail().equals(user.getEmail())) {
            log.info("Email has changed, update user with email {}", user.getEmail());
            if (isEmailAlreadyTaken(user.getEmail())) {
                throw new UserAlreadyRegisteredException();
            }
            existitingUser.setEmail(user.getEmail());
        }

        if (user.getPassword() != null && !user.getPassword().isEmpty() && !passwordEncoder.matches(existitingUser.getPassword(), user.getPassword())) {
            log.info("Password has changed, update new user password");
            existitingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return existitingUser;
    }
}