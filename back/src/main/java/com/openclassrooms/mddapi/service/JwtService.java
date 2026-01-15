package com.openclassrooms.mddapi.service;

import java.time.Instant;

public interface JwtService {

    public String generateJwtToken(String email);

    public Instant getExpirationTime(String token);

}