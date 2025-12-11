package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.model.Comment;

import java.util.List;

public interface CommentService {

    Comment save(CommentDto commentDto, String userName);

    List<CommentDto> findCommentsByPostId(Long id);
}