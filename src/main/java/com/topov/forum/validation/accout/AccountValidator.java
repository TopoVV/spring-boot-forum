package com.topov.forum.validation.accout;

import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.ValidationResultFactory;
import com.topov.forum.validation.accout.validation.ConfirmationTokenValidationRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

@Service
public class AccountValidator {
    private final Validator validator;
    private final ValidationResultFactory validationResultFactory;

    @Autowired
    public AccountValidator(Validator validator, ValidationResultFactory validationResultFactory) {
        this.validator = validator;
        this.validationResultFactory = validationResultFactory;
    }

    public ValidationResult validate(ConfirmationTokenValidationRule validationRule) {
        final var violations = validator.validate(validationRule);
        return validationResultFactory.createValidationResult(violations);
    }
}
