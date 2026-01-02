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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(summary = "Generate a token", description = "Generate a token when user tries to login if authenticated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AuthSuccessDto.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
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

    @Operation(summary = "Register a new user", description = "Register a new user in the database and generate a token for them")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AuthSuccessDto.class))}),
            @ApiResponse(responseCode = "409", content = @Content(mediaType = "text/plain",
                    examples = @ExampleObject(value="user already registered")))})
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

    @Operation(summary = "Get user information", description = "Return logged in user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/me")
    public UserDto userInfo() throws ResourceNotFoundException {
        log.info("GET api/auth/me called -> start the process to get user info");
        UserDto user =  this.userService.findUser();
        log.info("User retrieved successfully");
        return  user;
    }

    @Operation(summary = "Check if email is already taken", description = "Return true if the email is already taken, otherwise false")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("email/{userMail}")
    public Boolean checkIfEmailAlreadyTaken(@PathVariable String userMail){
        return this.userService.isEmailAlreadyTaken(userMail);
    }

    @Operation(summary = "Check if email is already taken", description = "Return true if the username is already taken, otherwise false")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("username/{userName}")
    public Boolean checkIfUserNameAlreadyTaken(@PathVariable String userName){
        return this.userService.isUserNameAlreadyTaken(userName);
    }

}