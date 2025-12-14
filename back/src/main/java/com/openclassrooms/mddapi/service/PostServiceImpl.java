package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, UserService userService, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.postMapper = postMapper;
    }

    @Override
    public Optional<Post> findPostById(Long postId) {
        return postRepository.findById(postId);
    }

    /**
     * Retrieve all posts from topics the user is subscribed to
     * @return list of postDto
     * @throws ResourceNotFoundException if user not found
     */
    @Override
    public List<PostDto> getPostsBySubscribedTopics() throws ResourceNotFoundException {

        log.info("Try to retrieve all posts from topics the user is subscribed to");

        User loggedUser = this.userService.getLoggedUser();

        List<Topic> subscribedTopics = loggedUser.getTopics();

        List <PostDto> posts =  this.postMapper.asPostDtos(this.postRepository.findByTopicInOrderByCreatedAtDesc(subscribedTopics));

        return posts;
    }
}