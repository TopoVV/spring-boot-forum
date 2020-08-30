package com.topov.forum.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class PostDto {
    private Long postId;
    private String title;
    private String text;
    private String author;
    private Integer visitsAmount;
    private Integer commentsAmount;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CommentDto> comments;
}
