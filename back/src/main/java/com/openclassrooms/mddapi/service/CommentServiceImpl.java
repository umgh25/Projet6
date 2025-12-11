package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService  {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostService postService;
    private final UserService userService;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, PostService postService, UserService userService) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.postService = postService;
        this.userService = userService;
    }

    @Override
    public Comment save(CommentDto commentDto, String username) {
        log.info("Try to save new comment");
        User user = this.userService.findUserByMail(username).orElse(null);
        Comment commentToSave = commentMapper.asComment(commentDto, postService);
        commentToSave.setAuthor(user);
        commentToSave.setCreatedAt(LocalDate.now());
        return this.commentRepository.save(commentToSave);
    }

    @Override
    public List<CommentDto> findCommentsByPostId(Long id) {
        return commentMapper.asCommentDtos(this.commentRepository.findByPostId(id));
    }
}