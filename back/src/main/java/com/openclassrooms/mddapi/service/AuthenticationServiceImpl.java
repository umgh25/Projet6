package com.openclassrooms.mddapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication authenticate(String email, String password) {

        log.info("Create authentication token");
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        log.info("Check user authentication");
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

        return authentication;
    }
}