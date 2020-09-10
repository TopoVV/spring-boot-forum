package com.topov.forum.service.account;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.result.OperationResult;
import com.topov.forum.dto.result.account.AccountConfirmationResult;
import com.topov.forum.service.token.AccountConfirmationTokenService;
import com.topov.forum.service.user.UserService;
import com.topov.forum.token.AccountConfirmationToken;
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.ValidationService;
import com.topov.forum.validation.accout.rule.ConfirmationTokenValidationRule;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountConfirmationTokenService confirmationTokenService;
    private final ValidationService validationService;
    private final UserService userService;

    @Autowired
    public AccountServiceImpl(AccountConfirmationTokenService confirmationTokenService,
                              ValidationService validationService,
                              UserService userService) {
        this.confirmationTokenService = confirmationTokenService;
        this.validationService = validationService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public AccountConfirmationToken createAccountConfirmationToken(String username) {
        log.debug("Creating a registration confirmation token for user: {}", username);
        return confirmationTokenService.createConfirmationToken(username);
    }

    @Override
    @Transactional
    public OperationResult confirmAccount(String tokenValue) {
        log.debug("Confirmation of the account");
        try {
            final ConfirmationTokenValidationRule validationRule = new ConfirmationTokenValidationRule(tokenValue);
            final ValidationResult validationResult = validationService.validate(validationRule);
            if (validationResult.containsErrors()) {
                final List<Error> validationErrors = validationResult.getValidationErrors();
                return new AccountConfirmationResult(HttpStatus.BAD_REQUEST, validationErrors, "Account is not confirmed");
            }

            final AccountConfirmationToken token = confirmationTokenService.getConfirmationToken(tokenValue);

            userService.enableUser(token.getUsername());
            confirmationTokenService.revokeConfirmationToken(tokenValue);

            return new AccountConfirmationResult(HttpStatus.OK, "Account confirmed");

        } catch (RuntimeException e) {
            log.error("Something gone wrong when confirming account", e);
            return new AccountConfirmationResult(HttpStatus.BAD_REQUEST, "Account not confirmed. Try again later");
        }
    }
}
