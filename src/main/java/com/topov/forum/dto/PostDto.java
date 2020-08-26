package com.topov.forum.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
public class PostDto {
    private Long postId;
    private String title;
    private String text;
    private String author;
    private BigInteger views;
    private List<CommentDto> comments;
}
