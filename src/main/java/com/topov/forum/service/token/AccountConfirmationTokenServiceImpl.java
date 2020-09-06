package com.topov.forum.service.token;

import com.topov.forum.repository.AccountConfirmationTokenRepository;
import com.topov.forum.token.AccountConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.Optional;

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

    public AccountConfirmationToken getConfirmationToken(String tokenValue) {
        return accountConfirmationTokenRepository.findTokenByTokenValue(tokenValue)
            .orElseThrow(EntityExistsException::new);
    }

    @Override
    @Transactional
    public void revokeConfirmationToken(String token) {

    }
}
