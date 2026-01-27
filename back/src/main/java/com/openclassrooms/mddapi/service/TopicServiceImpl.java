package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.TopicWithSubscriptionStatusDto;
import com.openclassrooms.mddapi.dto.UserTopicsSubscribedDto;
import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the topic service.
 * Handles topic-related business logic including retrieval, subscription, and unsubscription.
 */
@Slf4j
@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private final TopicMapper topicMapper;

    /**
     * Constructor for TopicServiceImpl.
     *
     * @param topicRepository Repository for topic data access
     * @param subscriptionRepository Repository for subscription data access
     * @param userService Service for managing users
     * @param topicMapper Mapper for converting between Topic and TopicDto
     */
    public TopicServiceImpl(TopicRepository topicRepository, SubscriptionRepository subscriptionRepository, UserService userService, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
        this.topicMapper = topicMapper;
    }

    /**
     * Retrieve a topic by its ID
     *
     * @param topicId
     * @return an optional containing the topic if found
     */
    @Override
    public Optional<Topic> findTopicById(Long topicId) {
        return this.topicRepository.findById(topicId);
    }

    /**
     * Retrieves all available topics.
     *
     * @return List of all topics as DTOs
     */
    @Override
    public List<TopicDto> findAllTopic() {
        return this.topicMapper.asTopicDtos(this.topicRepository.findAll());
    }

    /**
     * Retrieve a list of all topics
     * @return list of topicDto
     * @throws ResourceNotFoundException if user not found
     */
    @Override
    public List<TopicWithSubscriptionStatusDto> findAllTopicsWithSubscriptionStatus() throws ResourceNotFoundException {

        User userLogged = userService.getLoggedUser();

        List<Topic> topics = this.topicRepository.findAll();

        return mapTopicsToDtosWithSubscriptionStatus(topics, userLogged);
    }

    /**
     * Map all topics to dto including user subscription status
     * @param topics list of all topics
     * @param userLogged user logged into the application
     * @return List of topicDto including user subscription status
     */
    private List<TopicWithSubscriptionStatusDto> mapTopicsToDtosWithSubscriptionStatus(List<Topic> topics, User userLogged) {
        return topics.stream().map(topic -> {
            boolean hasAlreadySubscribed = subscriptionRepository.existsByUserAndTopic(userLogged, topic);

            return TopicWithSubscriptionStatusDto.builder()
                    .id(topic.getId())
                    .description(topic.getDescription())
                    .title(topic.getTitle())
                    .subscribed(hasAlreadySubscribed)
                    .build();
        }).toList();
    }

    /**
     * Retrieve all topics a user is subscribed to
     * @return list of UserTopicsSubscribedDto
     * @throws ResourceNotFoundException if user not found
     */
    @Override
    public List<UserTopicsSubscribedDto> getSubscribedTopicsByUser() throws ResourceNotFoundException {
        log.info("Try to retrieve topics subscribed by a user");
        User userLogged = userService.getLoggedUser();

        List<Topic> topics = this.topicRepository.findAll();

        return mapTopicsToDtos(topics, userLogged);
    }

    /**
     * Filter the list of all topics to keep only those for
     * which a user is subscribed and maps them to dto
     * @param topics List of all topics
     * @param userLogged user logged into the application
     * @return list of topicDto for which a user is subscribed
     */
    private List<UserTopicsSubscribedDto> mapTopicsToDtos(List<Topic> topics, User userLogged) {
        return topics.stream().filter(
                topic -> subscriptionRepository.existsByUserAndTopic(userLogged, topic)
        ).map(
                topic -> UserTopicsSubscribedDto.builder()
                        .id(topic.getId())
                        .title(topic.getTitle())
                        .description(topic.getDescription())
                        .build()
        ).toList();
    }

    /**
     * Allow subscribing to a topic
     * @param topicId topic's id
     * @throws ResourceNotFoundException if user or topic isn't found
     * @throws BadRequestException if a user is already subscribed
     */
    @Override
    @Transactional
    public void subscribeTopic(Long topicId) throws ResourceNotFoundException, BadRequestException {

        log.info("Try to subscribe to topic with id {}", topicId);

        User userLogged = userService.getLoggedUser();
        Topic topic = getTopicById(topicId);

        boolean hasAlreadySubscribed = subscriptionRepository.existsByUserAndTopic(userLogged, topic);

        if (hasAlreadySubscribed) {
            log.error("User is already subscribed");
            throw new BadRequestException("Déjà abonné à ce topic");
        }

        Subscription subscription = Subscription.builder()
                .user(userLogged)
                .topic(topic)
                .build();
        subscriptionRepository.save(subscription);
        log.info("User {}'s subscription to the topic {} has been successfully added", userLogged.getUserName(), topicId);
    }

    /**
     * Allow unsubscribing from a topic
     * @param topicId topic'id
     * @throws ResourceNotFoundException if user or topic isn't found
     * @throws BadRequestException if a user is not subscribed
     */
    @Override
    @Transactional
    public void unsubscribeTopic(Long topicId) throws ResourceNotFoundException, BadRequestException {

        log.info("Try to unsubscribe to topic with id {}", topicId);

        User userLogged = userService.getLoggedUser();
        Topic topic = getTopicById(topicId);

        Subscription subscription = subscriptionRepository.findByUserAndTopic(userLogged, topic)
                .orElseThrow(() -> {
                    log.error("User has not subscribed");
                    return new BadRequestException("Pas abonné à ce topic");
                });

        subscriptionRepository.delete(subscription);
        log.info("User {} unsubscribed from topic {} successfully", userLogged.getUserName(), topicId);
    }


    /**
     * Retrieves a topic by its identifier.
     *
     * @param topicId Topic identifier
     * @return The topic
     * @throws ResourceNotFoundException If the topic is not found
     */
    private Topic getTopicById(Long topicId) throws ResourceNotFoundException {
        return this.topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
    }
}