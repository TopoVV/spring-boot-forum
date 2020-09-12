package com.topov.forum.validation.comment.validator;

import com.topov.forum.repository.CommentRepository;
import com.topov.forum.validation.comment.constraint.CommentExists;
import com.topov.forum.validation.comment.rule.CommentEditValidationRule;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CommentExistsConstraintValidator implements ConstraintValidator<CommentExists, CommentEditValidationRule> {
    private CommentRepository commentRepository;

    @Override
    public boolean isValid(CommentEditValidationRule validation, ConstraintValidatorContext ctx) {
        if (!commentRepository.existsById(validation.getCommentId())) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                .addPropertyNode("comment")
                .addConstraintViolation();
            return false;
        }
        return true;
    }

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
