package com.topov.forum.service;

import com.topov.forum.dto.AccountConfirmationFail;
import com.topov.forum.dto.AccountConfirmationResponse;
import com.topov.forum.dto.AccountConfirmationSuccess;
import com.topov.forum.service.token.ConfirmationTokenService;
import com.topov.forum.service.user.UserService;
import com.topov.forum.token.ConfirmationToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
public class AccountConfirmationServiceImpl implements AccountConfirmationService {
    private final ConfirmationTokenService confirmationTokenService;
    private final UserService userService;

    @Autowired
    public AccountConfirmationServiceImpl(ConfirmationTokenService confirmationTokenService,
                                          UserService userService) {
        this.confirmationTokenService = confirmationTokenService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public AccountConfirmationResponse confirmAccount(String tokenValue) {
        log.debug("Confirmation of the account");
        final Optional<ConfirmationToken> optionalToken = confirmationTokenService.getAccountConfirmationToken(tokenValue);

        if (optionalToken.isPresent()) {
            final ConfirmationToken confirmationToken = optionalToken.get();

            if (confirmationToken.isTokenValid()) {
                userService.enableUser(confirmationToken.getUsername());
                confirmationTokenService.revokeConfirmationToken(confirmationToken.getTokenValue());
                return new AccountConfirmationSuccess(HttpStatus.OK, "Account confirmed");
            }
        }

        return new AccountConfirmationFail(HttpStatus.BAD_REQUEST, "Account is not confirmed", "Invalid token");
    }
}
