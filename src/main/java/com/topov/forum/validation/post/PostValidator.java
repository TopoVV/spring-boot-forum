package com.topov.forum.validation.post;

import com.topov.forum.dto.error.ValidationError;
import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.validation.post.groups.PostCreationChecks;
import com.topov.forum.validation.post.groups.PostModificationChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class PostValidator {
    private final Validator validator;

    @Autowired
    public PostValidator(Validator validator) {
        this.validator = validator;
    }

    public ValidationResult validate(PostCreateRequest postCreateRequest) {
        List<ValidationError> validationErrors = new ArrayList<>();
        final Set<ConstraintViolation<PostCreateRequest>> violations = validator.validate(postCreateRequest, PostCreationChecks.class);
        for (final var violation : violations) {
            final String invalid = violation.getPropertyPath().toString();
            final String description = violation.getMessage();
            validationErrors.add(new ValidationError(invalid, description));
        }
        return new ValidationResult(validationErrors);
    }

    public ValidationResult validate(PostEditRequest postEditRequest) {
        List<ValidationError> validationErrors = new ArrayList<>();
        final Set<ConstraintViolation<PostEditRequest>> violations = validator.validate(postEditRequest, PostModificationChecks.class);
        for (final var violation : violations) {
            final String invalid = violation.getPropertyPath().toString();
            final String description = violation.getMessage();
            validationErrors.add(new ValidationError(invalid, description));
        }
        return new ValidationResult(validationErrors);
    }
}
