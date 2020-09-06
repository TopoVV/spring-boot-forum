package com.topov.forum.validation.post;

import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.ValidationResultFactory;
import com.topov.forum.validation.post.rules.PostCreateValidation;
import com.topov.forum.validation.post.rules.PostEditValidation;
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
        final PostCreateValidation validation = new PostCreateValidation(postCreateRequest.getTitle());
        final var violations = validator.validate(validation);
        return validationResultFactory.createValidationResult(violations);
    }

    public ValidationResult validate(Long postId, PostEditRequest postEditRequest) {
        final PostEditValidation validation = new PostEditValidation(postId, postEditRequest);
        final var violations = validator.validate(validation);
        return validationResultFactory.createValidationResult(violations);
    }
}
