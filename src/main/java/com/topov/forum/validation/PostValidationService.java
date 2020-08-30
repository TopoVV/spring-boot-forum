package com.topov.forum.validation;

import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.request.post.PostEditRequest;

public interface PostValidationService {
    ValidationResult validateCreatePost(PostCreateRequest postCreateRequest);
    ValidationResult validateEditPost(PostEditRequest postEditRequest);
}
