package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.service.PostService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", imports = {PostService.class})
public interface CommentMapper {

    @Mapping(target = "post", expression = "java(postService.findPostById(commentDto.getPostId()).orElse(null))")
    @Mapping(target = "author", ignore = true )
    Comment asComment(CommentDto commentDto, PostService postService);

    @Mapping(target = "author", expression = "java(comment.getAuthor() != null ? comment.getAuthor().getUserName() : null)")
    CommentDto asCommentDto(Comment comment);
    List<CommentDto> asCommentDtos (List<Comment> comments);

}