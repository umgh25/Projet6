package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class CommentDto {

    @NotBlank
    private String content;

    private Long postId;

    private String author;

}
