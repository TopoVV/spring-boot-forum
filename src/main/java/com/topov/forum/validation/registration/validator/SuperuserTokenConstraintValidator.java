package com.topov.forum.validation.registration.validator;

import com.topov.forum.repository.SuperuserTokenRepository;
import com.topov.forum.token.SuperuserToken;
import com.topov.forum.validation.registration.constraint.SuperuserTokenValid;
import com.topov.forum.validation.registration.rules.SuperuserRegistrationValidation;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.lang.model.type.ErrorType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class SuperuserTokenConstraintValidator
    implements ConstraintValidator<SuperuserTokenValid, SuperuserRegistrationValidation> {

    private SuperuserTokenRepository tokenRepository;

    @Autowired
    public void setTokenRepository(SuperuserTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public boolean isValid(SuperuserRegistrationValidation validation, ConstraintValidatorContext ctx) {
        final String token = validation.getTokenValue();
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
