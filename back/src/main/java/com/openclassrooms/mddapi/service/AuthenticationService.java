package com.openclassrooms.mddapi.service;

import org.springframework.security.core.Authentication;

/**
 * Service interface for user authentication.
 */
public interface AuthenticationService {

    /**
     * Authenticates a user with email and password.
     *
     * @param email User's email address
     * @param password User's password
     * @return Authentication object containing authentication details
     */
    public Authentication authenticate(String email, String password);

}