package com.topov.forum.validation.registration.validator;

import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;
import com.topov.forum.repository.SuperuserTokenRepository;
import com.topov.forum.token.SuperuserToken;
import com.topov.forum.validation.registration.constraint.ValidSuperuserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class SuperuserTokenConstraintValidator
    implements ConstraintValidator<ValidSuperuserToken, SuperuserRegistrationRequest> {

    private SuperuserTokenRepository tokenRepository;

    @Autowired
    public void setTokenRepository(SuperuserTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public boolean isValid(SuperuserRegistrationRequest superuserRegistrationRequest, ConstraintValidatorContext ctx) {
        return tokenRepository.findTokenByTokenValue(superuserRegistrationRequest.getToken())
            .map(SuperuserToken::isTokenValid)
            .orElse(false);
    }
}
