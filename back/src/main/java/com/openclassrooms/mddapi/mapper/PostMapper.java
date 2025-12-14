package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "author", expression = "java(post.getAuthor().getUserName())")
    PostDto asPostDto(Post post);

    List<PostDto> asPostDtos (List<Post> posts);
}
