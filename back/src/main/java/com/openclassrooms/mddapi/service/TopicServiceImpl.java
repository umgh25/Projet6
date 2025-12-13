package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService{

    private final TopicRepository topicRepository;
    private final UserService userService;

    public TopicServiceImpl(TopicRepository topicRepository, UserService userService) {
        this.topicRepository = topicRepository;
        this.userService = userService;
    }

    @Override
    public List<TopicDto> findAll() throws ResourceNotFoundException {

        User userLogged = userService.findUserByMail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ResourceNotFoundException::new);

        List<Topic> topics = this.topicRepository.findAll();


        return mapTopicsToDtosWithSubscriptionStatus(topics, userLogged);
    }

    private List<TopicDto> mapTopicsToDtosWithSubscriptionStatus(List<Topic> topics, User userLogged) {
        List<TopicDto> topicDtos = topics.stream().map(topic -> {
            boolean hasAlreadySubscribed = userLogged.getTopics().contains(topic);

            return TopicDto.builder()
                    .id(topic.getId())
                    .description(topic.getDescription())
                    .title(topic.getTitle())
                    .subscribed(hasAlreadySubscribed)
                    .build();
        }).toList();
        return topicDtos;
    }

    @Override
    public List<Topic> getSubscribedTopicsByUser(Long userId) {
        return null;
    }

    @Override
    public void subscribeTopic(Long topicId) {

    }

    @Override
    public void unsubscribeTopic(Long topicId) {

    }
}