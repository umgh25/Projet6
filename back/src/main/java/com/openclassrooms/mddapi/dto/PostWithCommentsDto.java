package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostWithCommentsDto {

    private Long id;

    private String title;

    private String topic;

    private String author;

    private String content;

    private LocalDate createdAt;

    private List<CommentDto> comments;


}