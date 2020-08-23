package com.topov.forum.initialization;

import com.topov.forum.dto.request.RegistrationRequest;
import com.topov.forum.model.ForumUser;
import com.topov.forum.repository.UserRepository;
import com.topov.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserInitialization implements ApplicationRunner {
    private final UserService userService;
    private final PasswordEncoder encoder;

    @Autowired
    public UserInitialization(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
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
