package com.topov.forum.service;

import com.topov.forum.repository.RegistrationTokenRepository;
import com.topov.forum.token.RegistrationToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TokenServiceImpl implements TokenService {
    private final RegistrationTokenRepository registrationTokenRepository;

    @Autowired
    public TokenServiceImpl(RegistrationTokenRepository registrationTokenRepository) {
        this.registrationTokenRepository = registrationTokenRepository;
    }

    @Override
    public RegistrationToken createRegistrationToken(String username) {
        log.debug("Creating a registration confirmation token for user: {}", username);
        return registrationTokenRepository.save(new RegistrationToken(username));
    }
}
