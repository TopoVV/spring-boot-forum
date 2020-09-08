package com.topov.forum.validation;

import com.topov.forum.validation.accout.constraint.AccountConfirmationTokenValid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

@Service
public class AccountValidator_V2 {
    private final Validator validator;
    private final ValidationResultFactory validationResultFactory;

    @Autowired
    public AccountValidator_V2(Validator validator, ValidationResultFactory validationResultFactory) {
        this.validator = validator;
        this.validationResultFactory = validationResultFactory;
    }

    public <T extends ValidationRule> ValidationResult validate(T validation) {
        final var violations = validator.validate(validation, validation.getValidationSequence());
        return validationResultFactory.createValidationResult(violations);
    }
}
