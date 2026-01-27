package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.model.Comment;

import java.util.List;

/**
 * Service interface for comment management.
 */
public interface CommentService {

    /**
     * Saves a new comment.
     *
     * @param commentDto Comment data to save
     * @param userName Username of the comment author
     * @return The saved comment
     */
    Comment save(CommentDto commentDto, String userName);

    /**
     * Finds all comments for a specific post.
     *
     * @param id Post identifier
     * @return List of comments
     */
    List<CommentDto> findCommentsByPostId(Long id);
}