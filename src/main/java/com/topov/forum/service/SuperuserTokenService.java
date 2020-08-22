package com.topov.forum.service;

import com.topov.forum.repository.SuperuserTokenRepository;
import com.topov.forum.token.SuperuserToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class SuperuserTokenService {
    private final SuperuserTokenRepository superuserTokenRepository;

    @Autowired
    public SuperuserTokenService(SuperuserTokenRepository superuserTokenRepository) {
        this.superuserTokenRepository = superuserTokenRepository;
    }

    @Transactional
    public SuperuserToken createSuperuserToken() {
        log.debug("Creating a superuser token");
        return superuserTokenRepository.save(new SuperuserToken());
    }

    @Transactional
    public boolean checkSuperuserToken(String token) {
        return superuserTokenRepository.findTokenByTokenValue(token)
                                       .map(SuperuserToken::isTokenValid)
                                       .orElse(false);
    }

    @Transactional
    public void revokeSuperuserToken(String token) {
        log.debug("Revoking a token");
        superuserTokenRepository.findTokenByTokenValue(token).ifPresent(SuperuserToken::revoke);
    }
}
