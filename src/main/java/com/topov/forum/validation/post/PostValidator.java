package com.topov.forum.validation.post;

import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.ValidationResultFactory;
import com.topov.forum.validation.post.validation.PostCreateValidationRule;
import com.topov.forum.validation.post.validation.PostEditValidationRule;
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

    public ValidationResult validate(PostCreateValidationRule validationRule) {
        final var violations = validator.validate(validationRule);
        return validationResultFactory.createValidationResult(violations);
    }

    public ValidationResult validate(PostEditValidationRule validationRule) {
        final var violations = validator.validate(validationRule, validationRule.getValidationSequence());
        return validationResultFactory.createValidationResult(violations);
    }
}
