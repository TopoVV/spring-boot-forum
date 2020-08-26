package com.topov.forum.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class EditCommentRequest {
    @NotNull
    private Long commentId;
    @NotEmpty(message = "A comment must have text")
    private String text;
}
