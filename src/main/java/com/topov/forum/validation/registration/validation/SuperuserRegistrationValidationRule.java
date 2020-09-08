package com.topov.forum.validation.registration.validation;

import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;
import com.topov.forum.validation.registration.constraint.SuperuserTokenValid;
import lombok.Getter;

import javax.validation.GroupSequence;

import static com.topov.forum.validation.registration.validation.SuperuserRegistrationValidationRule.TokenChecks;

@Getter
@SuperuserTokenValid(groups = { TokenChecks.class })
public class SuperuserRegistrationValidationRule extends RegistrationValidationRule {
    private final String tokenValue;
    public SuperuserRegistrationValidationRule(SuperuserRegistrationRequest registrationRequest) {
        super(registrationRequest.getUsername(), registrationRequest.getEmail());
        this.tokenValue = registrationRequest.getToken();
    }

    @Override
    public Class<?> getValidationSequence() {
        return SuperuserRegistrationChecksSequence.class;
    }

    public interface TokenChecks {}

    @GroupSequence({ TokenChecks.class, RegistrationChecks.class })
    private interface SuperuserRegistrationChecksSequence {}
}
