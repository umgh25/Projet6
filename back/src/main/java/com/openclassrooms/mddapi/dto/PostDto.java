package com.openclassrooms.mddapi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PostDto {

    private Long id;

    private String description;

    private String title;

    private String topic;

    private String author;

    private String content;

    private LocalDate createdAt;

}
