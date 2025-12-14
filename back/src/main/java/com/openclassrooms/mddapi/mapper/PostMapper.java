package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.CreatePostDto;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.service.TopicService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", imports = {TopicService.class})
public interface PostMapper {

    @Mappings({
            @Mapping(target = "author", expression = "java(post.getAuthor().getUserName())"),
            @Mapping(target = "topic", expression = "java(post.getTopic().getTitle())")
    })

    PostDto asPostDto(Post post);

    List<PostDto> asPostDtos (List<Post> posts);


    @Mapping(target = "topic", expression = "java(topicService.findTopicById(createPostDto.getTopicId()).orElse(null))")
    Post asPost (CreatePostDto createPostDto, TopicService topicService);
}