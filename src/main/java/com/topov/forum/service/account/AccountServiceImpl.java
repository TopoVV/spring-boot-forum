package com.topov.forum.service.account;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.result.OperationResult;
import com.topov.forum.dto.result.account.AccountConfirmationResult;
import com.topov.forum.repository.AccountConfirmationTokenRepository;
import com.topov.forum.service.user.UserService;
import com.topov.forum.token.AccountConfirmationToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountConfirmationTokenRepository accountConfirmationTokenRepository;
    private final UserService userService;

    @Autowired
    public AccountServiceImpl(AccountConfirmationTokenRepository accountConfirmationTokenRepository,
                              UserService userService) {
        this.accountConfirmationTokenRepository = accountConfirmationTokenRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public AccountConfirmationToken createAccountConfirmationToken(String username) {
        log.debug("Creating a registration confirmation token for user: {}", username);
        return accountConfirmationTokenRepository.save(new AccountConfirmationToken(username));
    }

    @Override
    @Transactional
    public OperationResult confirmAccount(String tokenValue) {
        log.debug("Confirmation of the account");
        final Optional<AccountConfirmationToken> optionalToken =
            accountConfirmationTokenRepository.findTokenByTokenValue(tokenValue);

        if (optionalToken.isPresent()) {
            final AccountConfirmationToken accountConfirmationToken = optionalToken.get();

            if (accountConfirmationToken.isTokenValid()) {
                userService.enableUser(accountConfirmationToken.getUsername());
                accountConfirmationToken.revoke();
                return new AccountConfirmationResult(HttpStatus.OK, "Account confirmed");
            }
        }
        final Error invalidToken = new Error("Provided token is invalid");
        return new AccountConfirmationResult(HttpStatus.BAD_REQUEST, invalidToken,"Account is not confirmed");
    }
}
