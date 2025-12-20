package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CreatePostDto;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.PostWithCommentsDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final TopicService topicService;
    private final PostMapper postMapper;
    private final SubscriptionRepository subscriptionRepository;

    public PostServiceImpl(PostRepository postRepository, UserService userService, TopicService topicService, PostMapper postMapper, SubscriptionRepository subscriptionRepository) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.topicService = topicService;
        this.postMapper = postMapper;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Optional<Post> findPostById(Long postId) {
        return postRepository.findById(postId);
    }

    @Override
    public PostWithCommentsDto findPostDtoById(Long postId) throws ResourceNotFoundException {
        Post post = this.findPostById(postId).orElseThrow(ResourceNotFoundException::new);
        return this.postMapper.asPostWithCommentDto(post);
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

        List<Topic> subscribedTopics = subscriptionRepository.findByUser(loggedUser)
                .stream()
                .map(Subscription::getTopic)
                .toList();

        List <PostDto> posts =  this.postMapper.asPostDtos(this.postRepository.findByTopicInOrderByCreatedAtDesc(subscribedTopics));

        return posts;
    }


    @Override
    public void createPost(CreatePostDto newPost) throws ResourceNotFoundException {
        User user = this.userService.getLoggedUser();
        Post post = this.postMapper.asPost(newPost, this.topicService);
        post.setAuthor(user);
        post.setCreatedAt(LocalDate.now());
        this.postRepository.save(post);
    }
}