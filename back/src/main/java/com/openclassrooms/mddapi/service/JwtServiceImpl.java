package com.openclassrooms.mddapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Implementation of the JWT service.
 * Handles JWT token generation and expiration time retrieval.
 */
@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    /**
     * Constructor for JwtServiceImpl.
     *
     * @param jwtEncoder Encoder for generating JWT tokens
     * @param jwtDecoder Decoder for parsing JWT tokens
     */
    public JwtServiceImpl(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    /**
     * Generates a token.
     *
     * @param email, the user mail.
     * @return jwt token as a string
     */
    @Override
    public String generateJwtToken(String email) {
        log.info("Generate jwt token");
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(email)
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    }

    /**
     * Get the expiration time of a JWT token
     *
     * @param token the JWT token
     * @return the expiration time as Instant
     */
    @Override
    public Instant getExpirationTime(String token) {
        log.info("Getting token expiration time");
        try {
            Jwt jwt = this.jwtDecoder.decode(token);
            return jwt.getExpiresAt();
        } catch (Exception e) {
            log.error("Error decoding token: {}", e.getMessage());
            // Return current time if token is invalid, so it will be cleaned up immediately
            return Instant.now();
        }
    }
}