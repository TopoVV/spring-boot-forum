package com.topov.forum.validation.comment;

import com.topov.forum.dto.request.comment.CommentCreateRequest;
import com.topov.forum.dto.request.comment.CommentEditRequest;
import com.topov.forum.dto.result.comment.CommentEditResult;
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.ValidationResultFactory;
import com.topov.forum.validation.ValidationRule;
import com.topov.forum.validation.comment.validation.CommentCreateValidation;
import com.topov.forum.validation.comment.validation.CommentEditValidation;
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

    public ValidationResult validatePostExists(CommentsGetAllValidation validationRule) {
        final var violations = validator.validate(validationRule);
        return validationResultFactory.createValidationResult(violations);
    }

    public ValidationResult validate(CommentCreateValidation validationRule) {
        final var violations = validator.validate(validationRule);
        return validationResultFactory.createValidationResult(violations);
    }

    public ValidationResult validate(CommentEditValidation validationRule) {
        final var violations = validator.validate(validationRule);
        return validationResultFactory.createValidationResult(violations);
    }
}
