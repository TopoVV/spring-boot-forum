package com.topov.forum.service.token;

import com.topov.forum.repository.AccountConfirmationTokenRepository;
import com.topov.forum.token.AccountConfirmationToken;
import com.topov.forum.token.Token;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;

@Log4j2
@Service
public class AccountConfirmationTokenServiceImpl implements AccountConfirmationTokenService {
    private final AccountConfirmationTokenRepository accountConfirmationTokenRepository;

    @Autowired
    public AccountConfirmationTokenServiceImpl(AccountConfirmationTokenRepository accountConfirmationTokenRepository) {
        this.accountConfirmationTokenRepository = accountConfirmationTokenRepository;
    }

    @Override
    @Transactional
    public AccountConfirmationToken createConfirmationToken(String username) {
        return accountConfirmationTokenRepository.save(new AccountConfirmationToken(username));
    }

    @Transactional
    public AccountConfirmationToken getConfirmationToken(String tokenValue) {
        log.debug("Creating the account confirmation token");
        return accountConfirmationTokenRepository.findTokenByTokenValue(tokenValue)
            .orElseThrow(EntityExistsException::new);
    }

    @Override
    @Transactional
    public void revokeConfirmationToken(String token) {
        log.debug("Revoking the account confirmation token");
        accountConfirmationTokenRepository.findTokenByTokenValue(token)
            .ifPresent(Token::revoke);
    }
}
