package com.topov.forum.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDto {
    private Long postId;
    private String title;
    private String text;
    private String author;
}
