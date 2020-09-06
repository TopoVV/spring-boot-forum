package com.topov.forum.validation.comment.validation;

import com.topov.forum.validation.ValidationRule;
import com.topov.forum.validation.comment.constraint.PostExists;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentsGetAllValidation extends ValidationRule {
    @PostExists
    private final Long postId;
}
