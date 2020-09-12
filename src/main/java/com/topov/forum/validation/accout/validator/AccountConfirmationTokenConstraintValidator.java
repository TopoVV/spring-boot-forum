package com.topov.forum.validation.accout.validator;

import com.topov.forum.repository.AccountConfirmationTokenRepository;
import com.topov.forum.token.AccountConfirmationToken;
import com.topov.forum.validation.accout.constraint.AccountConfirmationTokenValid;
import com.topov.forum.validation.accout.rule.ConfirmationTokenValidationRule;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountConfirmationTokenConstraintValidator
    implements ConstraintValidator<AccountConfirmationTokenValid, ConfirmationTokenValidationRule> {

    private AccountConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public boolean isValid(ConfirmationTokenValidationRule confirmationTokenValidationRule, ConstraintValidatorContext ctx) {
        final String tokenValue = confirmationTokenValidationRule.getTokenValue();
        final var optionalToken = confirmationTokenRepository.findTokenByTokenValue(tokenValue);
        if (optionalToken.isPresent()) {
            final AccountConfirmationToken confirmationToken = optionalToken.get();
            if (confirmationToken.isTokenValid()) {
                return true;
            }
        }

        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
            .addPropertyNode("token")
            .addConstraintViolation();
        return false;
    }

    @Autowired
    public void setConfirmationTokenRepository(AccountConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }
}
