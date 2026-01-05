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

@Slf4j
@RestController
@RequestMapping("api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

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