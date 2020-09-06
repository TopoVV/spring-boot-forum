package com.topov.forum.validation.post;

import com.topov.forum.validation.ValidationRule;
import com.topov.forum.validation.post.constraint.UniqueTitle;
import com.topov.forum.validation.post.group.PostCreationChecks;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class PostCreateValidation extends ValidationRule {
    @UniqueTitle
    private final String title;
}
