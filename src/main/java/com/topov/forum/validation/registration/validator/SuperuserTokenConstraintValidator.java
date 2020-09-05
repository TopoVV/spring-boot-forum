package com.topov.forum.validation.registration.validator;

import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;
import com.topov.forum.repository.SuperuserTokenRepository;
import com.topov.forum.token.SuperuserToken;
import com.topov.forum.validation.registration.constraint.SuperuserTokenValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class SuperuserTokenConstraintValidator
    implements ConstraintValidator<SuperuserTokenValid, SuperuserRegistrationRequest> {

    private SuperuserTokenRepository tokenRepository;

    @Autowired
    public void setTokenRepository(SuperuserTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public boolean isValid(SuperuserRegistrationRequest superuserRegistrationRequest, ConstraintValidatorContext ctx) {
        final String token = superuserRegistrationRequest.getToken();
        final Optional<SuperuserToken> optionalToken = tokenRepository.findTokenByTokenValue(token);
        if (optionalToken.isPresent()) {
            final SuperuserToken superuserToken = optionalToken.get();
            if (superuserToken.isTokenValid()) {
                return true;
            }
        }

        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
            .addPropertyNode("token")
            .addConstraintViolation();

        return false;
    }
}
