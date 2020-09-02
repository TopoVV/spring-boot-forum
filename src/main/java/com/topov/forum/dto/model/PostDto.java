package com.topov.forum.dto.model;

import lombok.Data;

@Data
public class PostDto {
    private Long postId;
    private String title;
    private String text;
    private String author;
    private Integer visitsAmount;
    private Integer commentsAmount;
}
