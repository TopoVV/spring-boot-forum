package com.topov.forum.validation.comment.validation;

import com.topov.forum.validation.comment.constraint.PostExists;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentCreateValidation {
    @PostExists
    private final Long postId;
}
