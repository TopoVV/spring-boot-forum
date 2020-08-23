package com.topov.forum.service;

import com.topov.forum.repository.ConfirmationTokenRepository;
import com.topov.forum.token.ConfirmationToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Transactional
    public ConfirmationToken createAccountConfirmationToken(String username) {
        log.debug("Creating a registration confirmation token for user: {}", username);
        return confirmationTokenRepository.save(new ConfirmationToken(username));
    }

    @Transactional
    public Optional<ConfirmationToken> getAccountConfirmationToken(String token) {
        log.debug("Receiving the registration token");
        return confirmationTokenRepository.findTokenByTokenValue(token);
    }

    @Transactional
    public void revokeConfirmationToken(String token) {
        log.debug("Revoking a token");
        confirmationTokenRepository.findTokenByTokenValue(token)
            .ifPresent(ConfirmationToken::revoke);
    }

}
