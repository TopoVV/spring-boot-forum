package com.topov.forum.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

@Service
public class ValidationService {
    private final Validator validator;
    private final ValidationResultFactory validationResultFactory;

    @Autowired
    public ValidationService(Validator validator, ValidationResultFactory validationResultFactory) {
        this.validator = validator;
        this.validationResultFactory = validationResultFactory;
    }

    public <T extends ValidationRule> ValidationResult validate(T validation) {
        final var violations = validator.validate(validation, validation.getValidationSequence());
        return validationResultFactory.createValidationResult(violations);
    }
}
