package com.topov.forum.validation.registration;

import com.topov.forum.dto.error.ValidationError;
import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.registration.group.RegistrationChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RegistrationValidator {
    private final Validator validator;

    @Autowired
    public RegistrationValidator(Validator validator) {
        this.validator = validator;
    }

    public ValidationResult validate(RegistrationRequest registrationRequest) {
        List<ValidationError> validationErrors = new ArrayList<>();
        final var violations = validator.validate(registrationRequest, RegistrationChecks.class);
        for (final var violation : violations) {
            final String invalid = violation.getPropertyPath().toString();
            final String description = violation.getMessage();
            validationErrors.add(new ValidationError(invalid, description));
        }
        return new ValidationResult(validationErrors);
    }
}
