package com.topov.forum.validation.post;

import com.topov.forum.validation.ValidationRule;
import com.topov.forum.validation.post.constraint.UniqueTitle;
import com.topov.forum.validation.post.group.PostModificationChecks;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostEditValidation extends ValidationRule {
    private final Long postId;
    @UniqueTitle
    private final String newTitle;
    private final String oldTitle;
}
