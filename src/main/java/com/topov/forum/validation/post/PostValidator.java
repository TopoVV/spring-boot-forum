package com.topov.forum.validation.post;

import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.exception.ValidationException;
import com.topov.forum.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostValidator {
    private final PostCreateValidator postCreateValidator;
    private final PostEditValidator postEditValidator;

    @Autowired
    public PostValidator(PostCreateValidator postCreateValidator, PostEditValidator postEditValidator) {
        this.postCreateValidator = postCreateValidator;
        this.postEditValidator = postEditValidator;
    }

    public void validatePostCreationRequest(PostCreateRequest request) {
        final ValidationResult validated = postCreateValidator.validate(request);
        if (!validated.isValid()) {
            throw new ValidationException("Post creation failed", "", validated.getValidationErrors());
        }
    }

    public void validatePostEditRequest(PostEditRequest request) {
        final ValidationResult validated = postEditValidator.validate(request);
        if (!validated.isValid()) {
            throw new ValidationException("Post modification failed", "", validated.getValidationErrors());
        }
    }
}
