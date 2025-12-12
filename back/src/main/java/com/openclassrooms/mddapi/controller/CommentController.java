package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.service.CommentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/post/{id}")
    ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long id) {
        log.info("Get api/comment/post/{} called -> start the process to retrieve all comments for post {}", id, id);
        List<CommentDto> commentsFound = this.commentService.findCommentsByPostId(id);
        log.info(commentsFound.isEmpty() ? "No comments found" : "{} comments successfully retrieved", commentsFound.size());
        return new ResponseEntity<>(commentsFound, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<?> saveComment(@Valid @RequestBody CommentDto commentDto, Principal principal) {
        log.info("POST api/comment called -> start the process to save new comment written by {} for post {}", principal.getName(), commentDto.getPostId());
        this.commentService.save(commentDto, principal.getName());
        log.info("Comment successfully saved");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
