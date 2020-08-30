package com.topov.forum.dto.request.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CommentEditRequest {
    @NotNull
    private Long targetCommentId;
    @NotEmpty(message = "A comment must have text")
    private String newText;
}
