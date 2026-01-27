package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.AuthSuccessDto;
import com.openclassrooms.mddapi.dto.LoginRequestDto;
import com.openclassrooms.mddapi.dto.RegisterRequestDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.exception.UserAlreadyRegisteredException;
import com.openclassrooms.mddapi.service.AuthenticationService;
import com.openclassrooms.mddapi.service.JwtService;
import com.openclassrooms.mddapi.service.TokenBlacklistService;
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

import java.time.Instant;

/**
 * Controller managing authentication and authorization endpoints.
 * Provides login, registration, user information retrieval, and logout functionalities.
 */
@Slf4j
@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private final UserService userService;
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;

    private final AuthenticationService authenticationService;

    /**
     * Constructor for AuthenticationController.
     *
     * @param userService Service for managing users
     * @param jwtService Service for managing JWT tokens
     * @param tokenBlacklistService Service for managing the token blacklist
     * @param authenticationService Service for authentication
     */
    public AuthenticationController(UserService userService, JwtService jwtService, TokenBlacklistService tokenBlacklistService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.tokenBlacklistService = tokenBlacklistService;
        this.authenticationService = authenticationService;
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param loginRequest Login credentials containing username and password
     * @return ResponseEntity containing the generated JWT token
     */
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

    /**
     * Registers a new user in the system and generates a JWT token.
     *
     * @param registerRequest Registration data containing username, email, and password
     * @return ResponseEntity containing the generated JWT token
     * @throws UserAlreadyRegisteredException If the email or username is already taken
     */
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

    /**
     * Retrieves information about the currently authenticated user.
     *
     * @return UserDto containing the user's information
     * @throws ResourceNotFoundException If the user is not found
     */
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

    /**
     * Checks if an email address is already in use by another user.
     *
     * @param userMail The email address to check
     * @return true if the email is already taken, false otherwise
     */
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

    /**
     * Checks if a username is already in use by another user.
     *
     * @param userName The username to check
     * @return true if the username is already taken, false otherwise
     */
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
    /**
     * Logs out a user by invalidating their JWT token.
     * The token is added to the blacklist to prevent its reuse.
     *
     * @param authorizationHeader Authorization header containing the JWT token
     * @return ResponseEntity with a confirmation message
     */    @Operation(summary = "Logout user", description = "Invalidate the JWT token by adding it to the blacklist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged out", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value="{\"message\": \"Logged out successfully\"}"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
        log.info("POST api/auth/logout called -> start the process to logout the user");
        
        // Extract token from Authorization header
        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        
        // Get token expiration time
        Instant expirationTime = this.jwtService.getExpirationTime(token);
        
        // Add token to blacklist
        this.tokenBlacklistService.blacklistToken(token, expirationTime);
        
        log.info("User logged out successfully - token added to blacklist");
        return ResponseEntity.ok("{\"message\": \"Logged out successfully\"}");
    }

}