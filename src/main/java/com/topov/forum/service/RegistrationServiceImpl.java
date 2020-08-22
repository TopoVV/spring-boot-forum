package com.topov.forum.service;

import com.topov.forum.dto.request.RegistrationRequest;
import com.topov.forum.dto.request.SuperuserRegistrationRequest;
import com.topov.forum.email.Mail;
import com.topov.forum.email.MailSender;
import com.topov.forum.model.ForumUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Log4j2
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final UserService userService;
    private final MailSender mailSender;
    private final TokenService tokenService;

    public RegistrationServiceImpl(UserService userService, MailSender mailSender, TokenService tokenService) {
        this.userService = userService;
        this.mailSender = mailSender;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public void registerUser(RegistrationRequest registrationRequest) {
        log.debug("Registration of the user {}", registrationRequest);
        try {
            final ForumUser newUser = new ForumUser(registrationRequest);
            final String confirmationUrl = createRegistrationConfirmationUrl(registrationRequest);
            final String emailContent = "Please, confirm the registration: " + confirmationUrl;
            final  Mail email = new Mail("Registration", registrationRequest.getEmail(), emailContent);

            userService.saveRegularUser(newUser);
            mailSender.sendMail(email);
        } catch(Exception e) {
            log.error("An exception happened during registration", e);
            throw new RuntimeException("Cannot register the user. Please, try again later");
        }
    }

    @Override
    @Transactional
    public void registerUser(SuperuserRegistrationRequest registrationRequest) {
        log.debug("Superuser registration");
        try {
            final ForumUser newUser = new ForumUser(registrationRequest);
            tokenService.revokeSuperuserToken(registrationRequest.getToken());
            userService.saveRegularUser(newUser);
        } catch(Exception e) {
            log.error("An exception happened during registration", e);
            throw new RuntimeException("Cannot register the user. Please, try again later");
        }
    }

    @Override
    @Transactional
    public boolean confirmRegistration(String token) {
        log.debug("Confirmation of the registered user");
        final var registrationToken = tokenService.getRegistrationToken(token);
        if(registrationToken.isTokenValid()) {
            userService.enableUser(registrationToken.getUsername());
            registrationToken.revoke();
            return true;
        }
        return false;
    }

    private String createRegistrationConfirmationUrl(RegistrationRequest registrationRequest) {
        final var registrationToken = tokenService.createRegistrationToken(registrationRequest.getUsername());
        final String tokenConfirmationPath = String.format("registration/%s", registrationToken.getToken());
        return UriComponentsBuilder.newInstance()
                                   .scheme("http")
                                   .host("localhost")
                                   .port("8080")
                                   .path(tokenConfirmationPath)
                                   .build()
                                   .toString();
    }
}
