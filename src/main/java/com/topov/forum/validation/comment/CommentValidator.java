package com.topov.forum.validation.comment;

import com.topov.forum.dto.request.comment.CommentCreateRequest;
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.ValidationResultFactory;
import com.topov.forum.validation.ValidationRule;
import com.topov.forum.validation.comment.validation.CommentCreateValidation;
import com.topov.forum.validation.comment.validation.CommentsGetAllValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class CommentValidator {
    private final Validator validator;
    private final ValidationResultFactory validationResultFactory;

    @Autowired
    public CommentValidator(Validator validator, ValidationResultFactory validationResultFactory) {
        this.validator = validator;
        this.validationResultFactory = validationResultFactory;
    }

    public ValidationResult validatePostExists(Long postId) {
        ValidationRule validation = new CommentsGetAllValidation(postId);
        final var violations = validator.validate(validation);
        return validationResultFactory.createValidationResult(violations);
    }

    /**
     *
     * @param commentCreateRequest - comment data (temporary not needed for validation)
     */
    public ValidationResult validate(Long postId, CommentCreateRequest commentCreateRequest) {
        final CommentCreateValidation validation = new CommentCreateValidation(postId);
        final var violations = validator.validate(validation);
        return validationResultFactory.createValidationResult(violations);
    }
}
