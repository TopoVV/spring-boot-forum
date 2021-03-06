package com.topov.forum.validation.post.rule;

import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.validation.ValidationRule;
import com.topov.forum.validation.post.constraint.TitleUnique;
import lombok.Getter;

@Getter
@TitleUnique
public class PostCreateValidationRule extends ValidationRule {
    private final String title;

    public PostCreateValidationRule(PostCreateRequest postCreateRequest) {
        this.title  = postCreateRequest.getTitle();
    }
}
