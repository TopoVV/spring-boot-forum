package com.topov.forum.service.token;

import com.topov.forum.repository.ConfirmationTokenRepository;
import com.topov.forum.token.ConfirmationToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    @Transactional
    public ConfirmationToken createAccountConfirmationToken(String username) {
        log.debug("Creating a registration confirmation token for user: {}", username);
        return confirmationTokenRepository.save(new ConfirmationToken(username));
    }

    @Override
    @Transactional
    public Optional<ConfirmationToken> getAccountConfirmationToken(String token) {
        log.debug("Receiving the registration token");
        return confirmationTokenRepository.findTokenByTokenValue(token);
    }

    @Override
    @Transactional
    public void revokeConfirmationToken(String token) {
        log.debug("Revoking a token");
        confirmationTokenRepository.findTokenByTokenValue(token)
            .ifPresent(ConfirmationToken::revoke);
    }

}
