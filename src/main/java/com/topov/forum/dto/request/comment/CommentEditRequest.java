package com.topov.forum.dto.request.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class CommentEditRequest {
    @NotEmpty(message = "A comment must have text")
    private String newText;
}
