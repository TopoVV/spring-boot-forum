package com.topov.forum.initialization;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("!test")
public class UserInitialization implements ApplicationRunner {
    private final UserService userService;

    @Autowired
    public UserInitialization(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
//        RegistrationRequest superuser = new RegistrationRequest();
//        superuser.setUsername("super");
//        superuser.setPassword("super");
//        superuser.setEmail("email1@email.com");
//        userService.createSuperuser(superuser);
//
//        RegistrationRequest user = new RegistrationRequest();
//        user.setUsername("user");
//        user.setPassword("user");
//        user.setEmail("email2@email.com");
//        userService.createRegularUser(user);
//        userService.enableUser(user.getUsername());
    }
}
