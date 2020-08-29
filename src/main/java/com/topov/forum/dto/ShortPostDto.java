package com.topov.forum.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
public class ShortPostDto {
    private Long postId;
    private String title;
    private String author;
    private Integer visitsAmount;
    private Integer commentsAmount;
}
