package com.topov.forum.validation.comment.validation;

import com.topov.forum.validation.ValidationRule;
import com.topov.forum.validation.comment.constraint.CommentExists;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentEditValidation extends ValidationRule {
    @CommentExists
    private final Long commentId;
}
