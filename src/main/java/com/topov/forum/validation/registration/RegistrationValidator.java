package com.topov.forum.validation.registration;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;
import com.topov.forum.validation.ValidationResult;

public interface RegistrationValidator {
    ValidationResult validateSuperuserRegistration(SuperuserRegistrationRequest registrationRequest);

    ValidationResult validateRegularUserRegistration(RegistrationRequest registrationRequest);
}
