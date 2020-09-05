package com.topov.forum.validation.registration;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.ValidationResultFactory;
import com.topov.forum.validation.registration.group.TokenChecks;
import com.topov.forum.validation.registration.group.RegistrationChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.GroupSequence;
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

    public ValidationResult validate(RegistrationRequest registrationRequest) {
        final var violations = validator.validate(registrationRequest, RegistrationChecks.class);
        return validationResultFactory.createValidationResult(violations);
    }

    public ValidationResult validate(SuperuserRegistrationRequest registrationRequest) {
        final var violations = validator.validate(registrationRequest, SuperuserRegistrationChecks.class);
        return validationResultFactory.createValidationResult(violations);
    }

    @GroupSequence({ TokenChecks.class, RegistrationChecks.class })
    private interface SuperuserRegistrationChecks {}
}
