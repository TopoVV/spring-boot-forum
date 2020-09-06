package com.topov.forum.validation.registration;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.ValidationResultFactory;
import com.topov.forum.validation.registration.rules.RegistrationValidation;
import com.topov.forum.validation.registration.rules.SuperuserRegistrationValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.GroupSequence;
import javax.validation.Validator;

import static com.topov.forum.validation.registration.rules.RegistrationValidation.*;
import static com.topov.forum.validation.registration.rules.SuperuserRegistrationValidation.*;

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
        final RegistrationValidation validation = new RegistrationValidation(registrationRequest);
        final var violations = validator.validate(validation, RegistrationChecks.class);
        return validationResultFactory.createValidationResult(violations);
    }

    public ValidationResult validate(SuperuserRegistrationRequest registrationRequest) {
        final SuperuserRegistrationValidation validation = new SuperuserRegistrationValidation(registrationRequest);
        final var violations = validator.validate(validation, SuperuserRegistrationChecks.class);
        return validationResultFactory.createValidationResult(violations);
    }

    @GroupSequence({ TokenChecks.class, RegistrationChecks.class })
    private interface SuperuserRegistrationChecks {}
}
