package com.topov.forum.validation;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;

public interface UserValidationService {
    ValidationResult validateRegistration(RegistrationRequest registrationRequest);
    ValidationResult validateSuperuserRegistration(SuperuserRegistrationRequest registrationRequest);
}
