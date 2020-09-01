package com.topov.forum.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long commentId;
    private String text;
    private String author;
}
