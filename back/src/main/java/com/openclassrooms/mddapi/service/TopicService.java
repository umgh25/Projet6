package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.TopicWithSubscriptionStatusDto;
import com.openclassrooms.mddapi.dto.UserTopicsSubscribedDto;
import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.model.Topic;

import java.util.List;
import java.util.Optional;

public interface TopicService {

    List<TopicDto> findAllTopic();
    List<TopicWithSubscriptionStatusDto> findAllTopicsWithSubscriptionStatus() throws ResourceNotFoundException;

    List<UserTopicsSubscribedDto> getSubscribedTopicsByUser() throws ResourceNotFoundException;

    void subscribeTopic(Long topicId) throws ResourceNotFoundException, BadRequestException;

    void unsubscribeTopic(Long topicId) throws ResourceNotFoundException, BadRequestException;

    Optional<Topic> findTopicById (Long topicId);

}