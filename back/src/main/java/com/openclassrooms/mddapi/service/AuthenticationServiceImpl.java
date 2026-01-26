package com.openclassrooms.mddapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Implementation of the authentication service.
 * Handles user authentication using Spring Security's AuthenticationManager.
 */
@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    /**
     * Constructor for AuthenticationServiceImpl.
     *
     * @param authenticationManager Spring Security's authentication manager
     */
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Authenticates a user with email and password.
     *
     * @param email User's email address
     * @param password User's password
     * @return Authentication object containing authentication details
     */
    @Override
    public Authentication authenticate(String email, String password) {

        log.info("Create authentication token");
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        log.info("Check user authentication");
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

        return authentication;
    }
}