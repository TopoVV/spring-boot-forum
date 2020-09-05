package com.topov.forum.validation.post;

import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.ValidationResultFactory;
import com.topov.forum.validation.post.group.PostCreationChecks;
import com.topov.forum.validation.post.group.PostModificationChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;


@Service
public class PostValidator {
    private final Validator validator;
    private final ValidationResultFactory validationResultFactory;

    @Autowired
    public PostValidator(Validator validator, ValidationResultFactory validationResultFactory) {
        this.validator = validator;
        this.validationResultFactory = validationResultFactory;
    }

    public ValidationResult validate(PostCreateRequest postCreateRequest) {
        final var violations = validator.validate(postCreateRequest, PostCreationChecks.class);
        return validationResultFactory.createValidationResult(violations);
    }

    public ValidationResult validate(PostEditRequest postEditRequest) {
        final var violations = validator.validate(postEditRequest, PostModificationChecks.class);
        return validationResultFactory.createValidationResult(violations);
    }
}