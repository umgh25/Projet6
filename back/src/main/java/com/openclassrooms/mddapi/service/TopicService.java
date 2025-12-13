package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.UserTopicsSubscribedDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface TopicService {

    List<TopicDto> findAll() throws ResourceNotFoundException;

    List<UserTopicsSubscribedDto> getSubscribedTopicsByUser() throws ResourceNotFoundException;

    void subscribeTopic(Long topicId) throws ResourceNotFoundException, BadRequestException;

    void unsubscribeTopic(Long topicId) throws ResourceNotFoundException, BadRequestException;

}