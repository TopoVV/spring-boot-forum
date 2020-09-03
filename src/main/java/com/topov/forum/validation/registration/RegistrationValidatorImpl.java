package com.topov.forum.validation.registration;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;
import com.topov.forum.validation.ValidationResult;
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
    public ValidationResult validateSuperuserRegistration(SuperuserRegistrationRequest registrationRequest) {
        final ValidationResult validated = superuserTokenValidator.validate(registrationRequest.getToken());
<<<<<<< HEAD
        if (validated.containsErrors()) {
=======
        if (validated.isValid()) {
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
            return validated;
        }
        return validateRegistrationData(registrationRequest);
    }

    @Override
    public ValidationResult validateRegularUserRegistration(RegistrationRequest registrationRequest) {
        return validateRegistrationData(registrationRequest);
    }

    private ValidationResult validateRegistrationData(RegistrationRequest registrationRequest) {
        return registrationDataValidator.validate(registrationRequest);
    }
}
