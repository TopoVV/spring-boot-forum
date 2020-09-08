package com.topov.forum.validation.registration.validation;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.validation.ValidationRule;
import com.topov.forum.validation.registration.constraint.RegistrationRequestValid;
import lombok.Getter;

import static com.topov.forum.validation.registration.validation.RegistrationValidationRule.RegistrationChecks;

@Getter
@RegistrationRequestValid(groups = RegistrationChecks.class)
public class RegistrationValidationRule extends ValidationRule {
    private final String username;
    private final String email;

    public RegistrationValidationRule(RegistrationRequest registrationRequest) {
        this.username = registrationRequest.getUsername();
        this.email = registrationRequest.getEmail();
    }

    protected RegistrationValidationRule(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Override
    public Class<?> getValidationSequence() {
        return RegistrationChecks.class;
    }

    public interface RegistrationChecks {}
}
