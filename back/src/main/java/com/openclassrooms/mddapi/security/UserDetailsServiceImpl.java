package com.openclassrooms.mddapi.security;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of Spring Security's UserDetailsService.
 * Loads user-specific data for authentication.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor for UserDetailsServiceImpl.
     *
     * @param userRepository Repository for user data access
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads user details by username (email or username).
     * Searches first by email, then by username if not found.
     *
     * @param username Username or email of the user
     * @return UserDetails object containing user authentication information
     * @throws UsernameNotFoundException If the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(username);
        user = user.isPresent() ? user : userRepository.findByUserName(username);


        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.get().getEmail())
                .password(user.get().getPassword())
                .build();

    }

}