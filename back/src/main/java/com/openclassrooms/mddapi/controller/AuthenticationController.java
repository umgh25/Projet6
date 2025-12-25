package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.AuthSuccessDto;
import com.openclassrooms.mddapi.dto.LoginRequestDto;
import com.openclassrooms.mddapi.dto.RegisterRequestDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.exception.UserAlreadyRegisteredException;
import com.openclassrooms.mddapi.service.AuthenticationService;
import com.openclassrooms.mddapi.service.JwtService;
import com.openclassrooms.mddapi.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private final UserService userService;
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(UserService userService, JwtService jwtService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }


    @PostMapping("/login")
    public ResponseEntity<AuthSuccessDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {

        log.info("POST api/auth/login called -> start the process to log in the user");

        Authentication authentication = this.authenticationService.authenticate(loginRequest.getUserName(), loginRequest.getPassword());

        AuthSuccessDto token = AuthSuccessDto.builder()
                .token(this.jwtService.generateJwtToken(authentication.getName()))
                .build();

        log.info("User login successfully");
        return new ResponseEntity<>(token, HttpStatus.OK);
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

    @GetMapping("/me")
    public UserDto userInfo() throws ResourceNotFoundException {
        log.info("GET api/auth/me called -> start the process to get user info");
        UserDto user =  this.userService.findUser();
        log.info("User retrieved successfully");
        return  user;
    }

    @GetMapping("email/{userMail}")
    public Boolean checkIfEmailAlreadyTaken(@PathVariable String userMail){
        return this.userService.isEmailAlreadyTaken(userMail);
    }
    
    @GetMapping("username/{userName}")
    public Boolean checkIfUserNameAlreadyTaken(@PathVariable String userName){
        return this.userService.isUserNameAlreadyTaken(userName);
    }
}