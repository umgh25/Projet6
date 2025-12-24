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
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

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

    @Override
    public User updateUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public Optional<User> findUserByMail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User getLoggedUser() throws ResourceNotFoundException {
        return this.findUserByMail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ResourceNotFoundException::new);
    }


    @Override
    public UserDto findUser() throws ResourceNotFoundException {
        User user = this.getLoggedUser();
        return this.userMapper.asUserDto(user);
    }

    @Override
    public Boolean isEmailAlreadyTaken(String userMail) {
        return this.findUserByMail(userMail).isPresent();}

    /**
     * Check if user is already registered
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
}