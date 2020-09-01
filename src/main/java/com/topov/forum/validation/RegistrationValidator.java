package com.topov.forum.validation;

import com.topov.forum.dto.request.registration.RegistrationRequest;

public interface RegistrationValidator {
    void validateToken(String token);
    void validateRegistrationData(RegistrationRequest registrationRequest);
}
