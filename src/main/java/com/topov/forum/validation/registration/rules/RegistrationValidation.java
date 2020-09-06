package com.topov.forum.validation.registration.rules;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.validation.ValidationRule;
import com.topov.forum.validation.registration.constraint.RegistrationRequestValid;
import lombok.Getter;

import static com.topov.forum.validation.registration.rules.RegistrationValidation.RegistrationChecks;

@Getter
@RegistrationRequestValid(groups = RegistrationChecks.class)
public class RegistrationValidation extends ValidationRule {
    private final String username;
    private final String email;

    public RegistrationValidation(RegistrationRequest registrationRequest) {
        this.username = registrationRequest.getUsername();
        this.email = registrationRequest.getEmail();
    }

    protected RegistrationValidation(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public interface RegistrationChecks {}
}
