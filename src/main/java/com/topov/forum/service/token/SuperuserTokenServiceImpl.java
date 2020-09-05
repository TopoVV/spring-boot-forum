package com.topov.forum.service.token;

import com.topov.forum.repository.SuperuserTokenRepository;
import com.topov.forum.token.SuperuserToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class SuperuserTokenServiceImpl implements SuperuserTokenService {
    private final SuperuserTokenRepository superuserTokenRepository;

    @Autowired
    public SuperuserTokenServiceImpl(SuperuserTokenRepository superuserTokenRepository) {
        this.superuserTokenRepository = superuserTokenRepository;
    }

    @Override
    @Transactional
    public SuperuserToken createSuperuserToken() {
        log.debug("Creating a superuser token");
        return superuserTokenRepository.save(new SuperuserToken());
    }

    @Override
    @Transactional
    public void revokeSuperuserToken(String token) {
        log.debug("Revoking a token");
        superuserTokenRepository.findTokenByTokenValue(token)
            .ifPresent(SuperuserToken::revoke);
    }
}
