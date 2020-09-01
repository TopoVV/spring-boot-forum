package com.topov.forum.validation;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationValidatorImpl implements RegistrationValidator {
    private final RegistrationDataValidator registrationDataValidator;
    private final SuperuserTokenValidator superuserTokenValidator;

    @Autowired
    public RegistrationValidatorImpl(RegistrationDataValidator registrationDataValidator,
                                     SuperuserTokenValidator superuserTokenValidator) {
        this.registrationDataValidator = registrationDataValidator;
        this.superuserTokenValidator = superuserTokenValidator;
    }

    @Override
    public void validateToken(String token) {
        final ValidationResult validated = superuserTokenValidator.validate(token);
        if (!validated.isValid()) {
            throw new ValidationException(
                "Registration failed",
                "Provided superuser token is not accepted",
                validated.getValidationErrors()
            );
        }
    }

    @Override
    public void validateRegistrationData(RegistrationRequest registrationRequest) {
        final ValidationResult validated = registrationDataValidator.validate(registrationRequest);
        if (!validated.isValid()) {
            throw new ValidationException(
                "Registration failed",
                "Provided account data is not valid",
                validated.getValidationErrors()
            );
        }
    }
}
