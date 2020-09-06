package com.topov.forum.service.account;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.result.OperationResult;
import com.topov.forum.dto.result.account.AccountConfirmationResult;
import com.topov.forum.repository.AccountConfirmationTokenRepository;
import com.topov.forum.service.token.AccountConfirmationTokenService;
import com.topov.forum.service.user.UserService;
import com.topov.forum.token.AccountConfirmationToken;
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.accout.AccountValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountConfirmationTokenService confirmationTokenService;
    private final UserService userService;
    private final AccountValidator accountValidator;

    @Autowired
    public AccountServiceImpl(AccountConfirmationTokenService confirmationTokenService,
                              UserService userService,
                              AccountValidator accountValidator) {
        this.confirmationTokenService = confirmationTokenService;
        this.userService = userService;
        this.accountValidator = accountValidator;
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
            final ValidationResult validationResult = accountValidator.validate(tokenValue);
            if (validationResult.containsErrors()) {
                final List<Error> validationErrors = validationResult.getValidationErrors();
                return new AccountConfirmationResult(HttpStatus.BAD_REQUEST, validationErrors, "Account is not confirmed");
            }

            final AccountConfirmationToken token = confirmationTokenService.getConfirmationToken(tokenValue);

            userService.enableUser(token.getUsername());
            confirmationTokenService.revokeConfirmationToken(token.getTokenValue());
            return new AccountConfirmationResult(HttpStatus.OK, "Account confirmed");

        } catch (RuntimeException e) {
            log.error("Something gone wrong when confirming account", e);
            return new AccountConfirmationResult(HttpStatus.BAD_REQUEST, "Account not confirmed. Try again later");
        }
    }
}
