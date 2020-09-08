package com.topov.forum.validation.registration;

import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.ValidationResultFactory;
import com.topov.forum.validation.registration.validation.RegistrationValidationRule;
import com.topov.forum.validation.registration.validation.SuperuserRegistrationValidationRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

@Service
public class RegistrationValidator {
    private final Validator validator;
    private final ValidationResultFactory validationResultFactory;

    @Autowired
    public RegistrationValidator(Validator validator, ValidationResultFactory validationResultFactory) {
        this.validator = validator;
        this.validationResultFactory = validationResultFactory;
    }

    public ValidationResult validate(RegistrationValidationRule validationRule) {
        final var violations = validator.validate(validationRule, validationRule.getValidationSequence());
        return validationResultFactory.createValidationResult(violations);
    }

    public ValidationResult validate(SuperuserRegistrationValidationRule validationRule) {
        final var violations = validator.validate(validationRule, validationRule.getValidationSequence());
        return validationResultFactory.createValidationResult(violations);
    }

}
