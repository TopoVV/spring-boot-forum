package com.topov.forum.validation.registration.rules;

import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;
import com.topov.forum.validation.registration.constraint.SuperuserTokenValid;
import lombok.Getter;

import static com.topov.forum.validation.registration.rules.SuperuserRegistrationValidation.TokenChecks;

@Getter
@SuperuserTokenValid(groups = { TokenChecks.class })
public class  SuperuserRegistrationValidation extends RegistrationValidation {
    private final String tokenValue;
    public SuperuserRegistrationValidation(SuperuserRegistrationRequest registrationRequest) {
        super(registrationRequest.getUsername(), registrationRequest.getEmail());
        this.tokenValue = registrationRequest.getToken();
    }

    public interface TokenChecks {}
}
