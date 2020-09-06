package com.topov.forum.validation.accout;

import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.ValidationResultFactory;
import com.topov.forum.validation.accout.constraint.AccountConfirmationTokenValid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

@Service
public class AccountValidator {
    private final Validator validator;
    private final ValidationResultFactory validationResultFactory;

    @Autowired
    public AccountValidator(Validator validator, ValidationResultFactory validationResultFactory) {
        this.validator = validator;
        this.validationResultFactory = validationResultFactory;
    }

    public ValidationResult validate(String confirmationTokenValue) {
        final AccountConfirmationToken accountConfirmationToken = new AccountConfirmationToken(confirmationTokenValue);
        final var violations = validator.validate(accountConfirmationToken);
        return validationResultFactory.createValidationResult(violations);
    }

    @Getter
    @AccountConfirmationTokenValid
    private static final class AccountConfirmationToken {
        private final String tokenValue;

        @Autowired
        private AccountConfirmationToken(String tokenValue) {
            this.tokenValue = tokenValue;
        }
    }
}
