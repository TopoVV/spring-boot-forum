package com.topov.forum.validation.comment;

import com.topov.forum.validation.ValidationRule;
import com.topov.forum.validation.comment.constraint.PostExists;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

@Service
public class CommentValidator {
    private final Validator validator;

    @Autowired
    public CommentValidator(Validator validator) {
        this.validator = validator;
    }

    public boolean validatePostExists(Long postId) {
        ValidationRule rule = new PostExistsRule(postId);
        final var violations = validator.validate(rule);
        return violations.isEmpty();
    }

    @Getter
    @PostExists
    public static final class PostExistsRule extends ValidationRule {
        private final Long postId;
        private PostExistsRule(Long postId) {
            this.postId = postId;
        }
    }
}
