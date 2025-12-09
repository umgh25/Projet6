package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.AuthSuccessDto;
import com.openclassrooms.mddapi.dto.RegisterRequestDto;
import com.openclassrooms.mddapi.exception.UserAlreadyRegisteredException;
import com.openclassrooms.mddapi.service.JwtService;
import com.openclassrooms.mddapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthenticationController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthSuccessDto> register(@Valid @RequestBody RegisterRequestDto registerRequest) throws UserAlreadyRegisteredException {

        log.info("POST api/auth/register called -> start the process register a new user with mail {}", registerRequest.getEmail());

        this.userService.addUser(registerRequest);

        AuthSuccessDto token = AuthSuccessDto.builder()
                .token(this.jwtService.generateJwtToken(registerRequest.getEmail()))
                .build();

        log.info("User registered successfully");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}