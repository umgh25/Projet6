package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.UserTopicsSubscribedDto;
import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
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

@Slf4j
@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;

    public TopicServiceImpl(TopicRepository topicRepository, SubscriptionRepository subscriptionRepository, UserService userService) {
        this.topicRepository = topicRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
    }

    /**
     * Retrieve list of all topics
     * @return list of topicDto
     * @throws ResourceNotFoundException if user not found
     */
    @Override
    public List<TopicDto> findAll() throws ResourceNotFoundException {

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
    private List<TopicDto> mapTopicsToDtosWithSubscriptionStatus(List<Topic> topics, User userLogged) {
        return topics.stream().map(topic -> {
            boolean hasAlreadySubscribed = subscriptionRepository.existsByUserAndTopic(userLogged, topic);

            return TopicDto.builder()
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
     * @throws ResourceNotFoundException if user or topic not found
     * @throws BadRequestException if user is already subscribed
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
            throw new BadRequestException();
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
     * @throws ResourceNotFoundException if user or topic not found
     * @throws BadRequestException if user is not subscribed
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
                    return new BadRequestException();
                });

        subscriptionRepository.delete(subscription);
        log.info("User {} unsubscribed from topic {} successfully", userLogged.getUserName(), topicId);
    }

    @Override
    public Optional<Topic> findTopicById(Long topicId) {
        return this.topicRepository.findById(topicId);
    }

    private Topic getTopicById(Long topicId) throws ResourceNotFoundException {
        return this.topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
    }

}