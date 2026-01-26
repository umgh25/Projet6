package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CreatePostDto;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.PostWithCommentsDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller managing post-related endpoints.
 * Provides functionalities for creating, viewing, and listing posts.
 */
@Slf4j
@RestController
@RequestMapping("api/post")
public class PostController {

    private final PostService postService;

    /**
     * Constructor for PostController.
     *
     * @param postService Service for managing posts
     */
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Retrieves a post by its identifier with its comments.
     *
     * @param id Post identifier
     * @return PostWithCommentsDto containing the post and its comments
     * @throws ResourceNotFoundException If the post is not found
     */
    @Operation(summary = "Get post", description = "Return a post by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PostWithCommentsDto.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "text/plain",
                    examples = @ExampleObject(value="resource not found")))})
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")

    PostWithCommentsDto getPostById(@PathVariable Long id) throws ResourceNotFoundException {
        log.info("start the process to retrieve post by its id");
        PostWithCommentsDto postDto = this.postService.findPostDtoById(id);
        log.info("Process terminated successfully");
        return postDto;
    }

    /**
     * Retrieves all posts from topics the user is subscribed to.
     * Posts are sorted from newest to oldest.
     *
     * @return List of posts from subscribed topics
     * @throws ResourceNotFoundException If the user is not found
     */
    @Operation(summary = "Get posts", description = "Return all messages matching the topics the user is subscribed to, ordered from newest to oldest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema( schema = @Schema(implementation = PostDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "text/plain",
                    examples = @ExampleObject(value="resource not found")))})
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    List<PostDto> getPostsBySubscribedTopics() throws ResourceNotFoundException {
        log.info("start the process to retrieve all posts from topics the user is subscribed to");
        List<PostDto> postDtos = postService.getPostsBySubscribedTopics();
        log.info("Process terminated successfully, {} posts retrieved", postDtos.size());
        return postDtos;
    }


    /**
     * Creates a new post.
     *
     * @param newPost New post data to create
     * @return ResponseEntity with status 201 (Created)
     * @throws ResourceNotFoundException If the topic or user is not found
     */
    @Operation(summary = "create post", description = "Create a new post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "text/plain",
                    examples = @ExampleObject(value="resource not found")))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/create")
    ResponseEntity<?> createPost(@Valid @RequestBody CreatePostDto newPost) throws ResourceNotFoundException {
        log.info("start the process to create a new post");
        this.postService.createPost(newPost);
        log.info("Process to create new post terminated successfully");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}