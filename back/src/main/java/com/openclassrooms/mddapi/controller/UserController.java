package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.exception.UserAlreadyRegisteredException;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller managing user-related endpoints.
 * Provides functionalities for updating user information.
 */
@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * Constructor for UserController.
     *
     * @param userService Service for managing users
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Updates the currently authenticated user's information.
     *
     * @param user User data to update
     * @return ResponseEntity with status 200
     * @throws ResourceNotFoundException If the user is not found
     * @throws UserAlreadyRegisteredException If the email or username is already in use
     */
    @Operation(summary = "Update user", description = "Update user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "text/plain",
                    examples = @ExampleObject(value="resource not found"))),
            @ApiResponse(responseCode = "409", content = @Content(mediaType = "text/plain",
                    examples = @ExampleObject(value="user already registered")))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    ResponseEntity<?> updateUser(@RequestBody UserDto user) throws ResourceNotFoundException, UserAlreadyRegisteredException {
        log.info("GET api/user called -> start the process to update user");
        this.userService.updateUser(user);
        log.info(("User updated successfully"));
        return ResponseEntity.ok().build();
    }
}