package com.topov.forum.initialization;

import com.topov.forum.service.TokenService;
import com.topov.forum.token.SuperuserToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class SuperuserTokenInitialization implements ApplicationRunner {
    private final TokenService tokenService;

    @Autowired
    public SuperuserTokenInitialization(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void run(ApplicationArguments args) {
        SuperuserToken superuserToken = tokenService.createSuperuserToken();
        log.warn("SUPERUSER TOKEN: {}", superuserToken.getToken());
    }
}
