package com.topov.forum.service;

import com.topov.forum.repository.ConfirmationTokenRepository;
import com.topov.forum.repository.SuperuserTokenRepository;
import com.topov.forum.token.ConfirmationToken;
import com.topov.forum.token.SuperuserToken;
import com.topov.forum.token.Token;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Log4j2
@Service
public class TokenServiceImpl implements TokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final SuperuserTokenRepository superuserTokenRepository;

    @Autowired
    public TokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository,
                            SuperuserTokenRepository superuserTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.superuserTokenRepository = superuserTokenRepository;
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
    public SuperuserToken createSuperuserToken() {
        log.debug("Creating a superuser token");
        return superuserTokenRepository.save(new SuperuserToken());
    }

    @Override
    @Transactional
    public SuperuserToken getSuperuserToken(String token) {
        log.debug("Receiving the superuser token");
        return superuserTokenRepository.findTokenByTokenValue(token)
                                       .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public void revokeToken(Token token) {
        log.debug("Revoking a token");
        token.revoke();
    }

}
