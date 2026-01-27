package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Controller managing comment-related endpoints.
 * Provides functionalities for retrieving and creating comments on posts.
 */
@Slf4j
@RestController
@RequestMapping("api/comment")
public class CommentController {

    private final CommentService commentService;

    /**
     * Constructor for CommentController.
     *
     * @param commentService Service for managing comments
     */
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Retrieves all comments for a specific post.
     *
     * @param id Post identifier
     * @return ResponseEntity containing the list of comments
     */
    @Operation(summary = "Get comments", description = "Return all comments left on a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema( schema = @Schema(implementation = CommentDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/post/{id}")
    ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long id) {
        log.info("Get api/comment/post/{} called -> start the process to retrieve all comments for post {}", id, id);
        List<CommentDto> commentsFound = this.commentService.findCommentsByPostId(id);
        log.info(commentsFound.isEmpty() ? "No comments found" : "{} comments successfully retrieved", commentsFound.size());
        return new ResponseEntity<>(commentsFound, HttpStatus.OK);
    }

    /**
     * Saves a new comment for a post.
     *
     * @param commentDto Comment data to save
     * @param principal Currently authenticated user
     * @return ResponseEntity with status 201 (Created)
     */
    @Operation(summary = "save comment", description = "Save a new comment for a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    ResponseEntity<?> saveComment(@Valid @RequestBody CommentDto commentDto, Principal principal) {
        log.info("POST api/comment called -> start the process to save new comment written by {} for post {}", principal.getName(), commentDto.getPostId());
        this.commentService.save(commentDto, principal.getName());
        log.info("Comment successfully saved");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}