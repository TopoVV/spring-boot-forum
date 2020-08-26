package com.topov.forum.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
public class PostDto {
    private Long postId;
    private String title;
    private String text;
    private String author;
    private BigInteger views;
    private Integer commentsAmount;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CommentDto> comments;
}
