package com.topov.forum.service;

import com.topov.forum.dto.request.RegistrationRequest;
import com.topov.forum.email.Email;
import com.topov.forum.email.EmailSender;
import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Role;
import com.topov.forum.token.RegistrationToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.topov.forum.model.Role.*;

@Log4j2
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final UserService userService;
    private final EmailSender emailSender;
    private final TokenService tokenService;

    public RegistrationServiceImpl(UserService userService, EmailSender emailSender, TokenService tokenService) {
        this.userService = userService;
        this.emailSender = emailSender;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public void registerUser(RegistrationRequest registrationRequest) {
        log.debug("Registration of the user {}", registrationRequest);
        try {
            final ForumUser newUser = new ForumUser(registrationRequest);
            newUser.addRole(new Role(Roles.USER));
            userService.addUser(newUser);
            final var registrationToken = tokenService.createRegistrationToken(registrationRequest.getUsername());
            final String emailContent = "Please, confirm the registration: " + registrationToken.getToken();
            emailSender.sendEmail(new Email(registrationRequest.getEmail(), emailContent));
        } catch(Exception e) {
            log.error("An exception happened during registration", e);
            throw new RuntimeException("Cannot register the user. Please, try again later");
        }

    }

    @Override
    @Transactional
    public boolean confirmRegistration(String token) {
        final RegistrationToken registrationToken = tokenService.getToken(token);
        if(registrationToken.verifyToken()) {
            userService.enableUser(registrationToken.getUsername());
            return true;
        }
        return false;
    }
}
