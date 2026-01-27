package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CreatePostDto;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.PostWithCommentsDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.model.Post;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for post management.
 */
public interface PostService {

    /**
     * Finds a post by its identifier.
     *
     * @param postId Post identifier
     * @return Optional containing the post if found
     */
    Optional<Post> findPostById(Long postId);

    /**
     * Finds a post by its identifier and returns it as a DTO with comments.
     *
     * @param postId Post identifier
     * @return PostWithCommentsDto containing the post and its comments
     * @throws ResourceNotFoundException If the post is not found
     */
    PostWithCommentsDto findPostDtoById(Long postId) throws ResourceNotFoundException;

    /**
     * Retrieves all posts from topics the user is subscribed to.
     *
     * @return List of posts from subscribed topics
     * @throws ResourceNotFoundException If the user is not found
     */
    List<PostDto> getPostsBySubscribedTopics() throws ResourceNotFoundException;

    /**
     * Creates a new post.
     *
     * @param newPost Post data to create
     * @throws ResourceNotFoundException If the user or topic is not found
     */
    void createPost(CreatePostDto newPost) throws ResourceNotFoundException;

}