package com.topov.forum.service;

import com.topov.forum.repository.RegistrationTokenRepository;
import com.topov.forum.repository.SuperuserTokenRepository;
import com.topov.forum.token.RegistrationToken;
import com.topov.forum.token.SuperuserToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@Service
public class TokenServiceImpl implements TokenService {
    private final RegistrationTokenRepository registrationTokenRepository;
    private final SuperuserTokenRepository superuserTokenRepository;

    @Autowired
    public TokenServiceImpl(RegistrationTokenRepository registrationTokenRepository,
                            SuperuserTokenRepository superuserTokenRepository) {
        this.registrationTokenRepository = registrationTokenRepository;
        this.superuserTokenRepository = superuserTokenRepository;
    }

    @Override
    public RegistrationToken createRegistrationToken(String username) {
        log.debug("Creating a registration confirmation token for user: {}", username);
        return registrationTokenRepository.save(new RegistrationToken(username));
    }

    public SuperuserToken createSuperuserToken() {
        log.debug("Creating a superuser token");
        return superuserTokenRepository.save(new SuperuserToken());
    }

    @Override
    public void revokeSuperuserToken(String token) {
        log.debug("Revoking of the superuser token");
        superuserTokenRepository.findTokenByTokenValue(token)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                                .revoke();
    }

    @Override
    public RegistrationToken getRegistrationToken(String token) {
        return registrationTokenRepository.findTokenByTokenValue(token)
                                          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
