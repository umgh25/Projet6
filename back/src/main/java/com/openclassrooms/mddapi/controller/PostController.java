package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    PostDto getPostById(@PathVariable Long id) throws ResourceNotFoundException {
        log.info("start the process to retrieve post by its id");
        PostDto postDto = this.postService.findPostDtoById(id);
        log.info("Process terminated successfully");
        return postDto;
    }

    @GetMapping
    List<PostDto> getPostsBySubscribedTopics() throws ResourceNotFoundException {
        log.info("start the process to retrieve all posts from topics the user is subscribed to");
        List<PostDto> postDtos = postService.getPostsBySubscribedTopics();
        log.info("Process terminated successfully, {} posts retrieved", postDtos.size());
        return postDtos;
    }

}