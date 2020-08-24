package com.topov.forum.initialization;

import com.topov.forum.dto.request.RegistrationRequest;
import com.topov.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserInitialization implements ApplicationRunner {
    private final UserService userService;

    @Autowired
    public UserInitialization(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("username");
        request.setPassword("password");
        request.setEmail("email@email.com");
        userService.createSuperuser(request);
    }
}
