package com.topov.forum.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class CreateCommentRequest {
    private Long postId;
    @NotEmpty(message = "A comment must have text")
    private String text;
}
