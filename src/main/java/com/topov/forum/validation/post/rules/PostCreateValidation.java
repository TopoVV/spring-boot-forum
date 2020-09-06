package com.topov.forum.validation.post.rules;

import com.topov.forum.validation.ValidationRule;
import com.topov.forum.validation.post.constraint.TitleUnique;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@TitleUnique
public class PostCreateValidation extends ValidationRule {
    private final String title;
}
