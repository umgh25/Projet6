package com.openclassrooms.mddapi.service;

public interface JwtService {

    public String generateJwtToken(String email);

}