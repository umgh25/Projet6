package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.TopicWithSubscriptionStatusDto;
import com.openclassrooms.mddapi.dto.UserTopicsSubscribedDto;
import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.model.Topic;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for topic management.
 */
public interface TopicService {

    /**
     * Retrieves all available topics.
     *
     * @return List of all topics
     */
    List<TopicDto> findAllTopic();
    
    /**
     * Retrieves all topics with the user's subscription status.
     *
     * @return List of topics with subscription status
     * @throws ResourceNotFoundException If the user is not found
     */
    List<TopicWithSubscriptionStatusDto> findAllTopicsWithSubscriptionStatus() throws ResourceNotFoundException;

    /**
     * Retrieves all topics that the user is subscribed to.
     *
     * @return List of subscribed topics
     * @throws ResourceNotFoundException If the user is not found
     */
    List<UserTopicsSubscribedDto> getSubscribedTopicsByUser() throws ResourceNotFoundException;

    /**
     * Subscribes the user to a topic.
     *
     * @param topicId Topic identifier
     * @throws ResourceNotFoundException If the user or topic is not found
     * @throws BadRequestException If the user is already subscribed
     */
    void subscribeTopic(Long topicId) throws ResourceNotFoundException, BadRequestException;

    /**
     * Unsubscribes the user from a topic.
     *
     * @param topicId Topic identifier
     * @throws ResourceNotFoundException If the user or topic is not found
     * @throws BadRequestException If the user is not subscribed
     */
    void unsubscribeTopic(Long topicId) throws ResourceNotFoundException, BadRequestException;

    /**
     * Finds a topic by its identifier.
     *
     * @param topicId Topic identifier
     * @return Optional containing the topic if found
     */
    Optional<Topic> findTopicById (Long topicId);

}