package com.topov.forum.validation.post;

import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.request.post.PostEditRequest;
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

    public ValidationResult validatePostCreationRequest(PostCreateRequest request) {
        return postCreateValidator.validate(request);
    }

    public ValidationResult validatePostEditRequest(PostEditRequest request) {
        return postEditValidator.validate(request);
    }
}
