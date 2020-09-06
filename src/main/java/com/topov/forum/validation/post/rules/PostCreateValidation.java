package com.topov.forum.validation.post.rules;

import com.topov.forum.validation.ValidationRule;
import com.topov.forum.validation.post.constraint.UniqueTitle;
import com.topov.forum.validation.post.group.PostCreationChecks;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@UniqueTitle
public class PostCreateValidation extends ValidationRule {
    private final String title;
}
