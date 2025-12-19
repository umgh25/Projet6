package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CreatePostDto;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.PostWithCommentsDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Optional<Post> findPostById(Long postId);

    PostWithCommentsDto findPostDtoById(Long postId) throws ResourceNotFoundException;

    List<PostDto> getPostsBySubscribedTopics() throws ResourceNotFoundException;

    void createPost(CreatePostDto newPost) throws ResourceNotFoundException;

}