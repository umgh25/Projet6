package com.openclassrooms.mddapi.service;

import java.time.Instant;

/**
 * Service interface for JWT token management.
 */
public interface JwtService {

    /**
     * Generates a JWT token for a user.
     *
     * @param email User's email address
     * @return JWT token as a string
     */
    public String generateJwtToken(String email);

    /**
     * Gets the expiration time of a JWT token.
     *
     * @param token JWT token
     * @return Expiration time as Instant
     */
    public Instant getExpirationTime(String token);

}