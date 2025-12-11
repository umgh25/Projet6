package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Post;

import java.util.Optional;

public interface PostService {

    Optional<Post> findPostById(Long postId);

}