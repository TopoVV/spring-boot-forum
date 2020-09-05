package com.topov.forum.validation.accout.validator;

import com.topov.forum.repository.AccountConfirmationTokenRepository;
import com.topov.forum.token.AccountConfirmationToken;
import com.topov.forum.validation.accout.constraint.AccountConfirmationTokenValid;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class AccountConfirmationTokenConstraintValidator
    implements ConstraintValidator<AccountConfirmationTokenValid, AccountConfirmationToken> {

    private AccountConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public boolean isValid(AccountConfirmationToken accountConfirmationToken, ConstraintValidatorContext ctx) {
        final String tokenValue = accountConfirmationToken.getTokenValue();
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
