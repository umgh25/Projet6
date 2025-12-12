package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.RegisterRequestDto;
import com.openclassrooms.mddapi.exception.UserAlreadyRegisteredException;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(RegisterRequestDto registerRequest) throws UserAlreadyRegisteredException {
        log.info("Check if user is already registered");
        this.isUserAlreadyRegister(registerRequest.getEmail());

        User userToSave = User.builder()
                .userName(registerRequest.getUserName())
                .email(registerRequest.getEmail())
                .password(this.passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        log.info("Save user {} in database", userToSave.getEmail());
        return this.userRepository.save(userToSave);
    }

    @Override
    public Optional<User> findUserByMail(String email) {
        return this.userRepository.findByEmail(email);
    }

    private void isUserAlreadyRegister(String email) throws UserAlreadyRegisteredException {
        Optional<User> user = this.userRepository.findByEmail(email);
        if (user.isPresent()) {
            log.error("User is already registered");
            throw new UserAlreadyRegisteredException("User already registered");
        }
    }
}