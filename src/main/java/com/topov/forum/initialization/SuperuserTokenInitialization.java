package com.topov.forum.initialization;

import com.topov.forum.service.token.SuperuserTokenService;
import com.topov.forum.token.SuperuserToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@Profile("!test")
public class SuperuserTokenInitialization implements ApplicationRunner {
    private final SuperuserTokenService superuserTokenService;

    @Autowired
    public SuperuserTokenInitialization(SuperuserTokenService superuserTokenService) {
        this.superuserTokenService = superuserTokenService;
    }

    @Override
    public void run(ApplicationArguments args) {
        SuperuserToken superuserToken = superuserTokenService.createSuperuserToken();
        log.warn("SUPERUSER TOKEN: {}", superuserToken.getToken());
    }
}
