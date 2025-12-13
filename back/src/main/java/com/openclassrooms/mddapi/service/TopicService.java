package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.model.Topic;

import java.util.List;

public interface TopicService {

    List<TopicDto> findAll() throws ResourceNotFoundException;

    List<Topic> getSubscribedTopicsByUser(Long userId);

    void subscribeTopic(Long topicId);

    void unsubscribeTopic(Long topicId);

}