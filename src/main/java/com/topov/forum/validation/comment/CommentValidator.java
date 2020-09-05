package com.topov.forum.validation.comment;

import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.ValidationResultFactory;
import com.topov.forum.validation.comment.constraint.PostExists;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class CommentValidator {
    private final Validator validator;

    @Autowired
    public CommentValidator(Validator validator) {
        this.validator = validator;
    }

    public boolean validatePostExists(Long postId) {
        final var violations = validator.validate(new PostExistsRule(postId));
        return violations.isEmpty();
    }

    @Getter
    @PostExists
    public static final class PostExistsRule {
        private final Long postId;
        private PostExistsRule(Long postId) {
            this.postId = postId;
        }
    }
}
