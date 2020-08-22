package com.topov.forum.service;

import com.topov.forum.dto.request.RegistrationRequest;
import com.topov.forum.dto.request.SuperuserRegistrationRequest;
import com.topov.forum.email.Mail;
import com.topov.forum.email.MailSender;
import com.topov.forum.exception.RegistrationException;
import com.topov.forum.model.ForumUser;
import com.topov.forum.dto.response.AccountConfirmation;
import com.topov.forum.token.ConfirmationToken;
import com.topov.forum.token.SuperuserToken;
import com.topov.forum.token.Token;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Log4j2
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private static final String CONFIRMATION_MAIL_TEMPLATE = "Welcome to Forum, %s. " +
        "To confirm your account follow this link: %s";

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
            final Mail mail = createConfirmationMail(registrationRequest);

            userService.saveRegularUser(newUser);
            mailSender.sendMail(mail);
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
            final SuperuserToken superuserToken = tokenService.getSuperuserToken(registrationRequest.getToken());
            if(superuserToken.isTokenValid()) {
                tokenService.revokeToken(superuserToken);
                userService.saveSuperuser(newUser);
            }
        } catch(Exception e) {
            log.error("An exception happened during registration", e);
            throw new RuntimeException("Cannot register the user. Please, try again later");
        }
    }

    @Override
    @Transactional
    public AccountConfirmation confirmAccount(String token) {
        log.debug("Confirmation of the registered user");
        try {
            return tokenService.getAccountConfirmationToken(token)
                               .map(this::doConfirmation)
                               .orElse(new AccountConfirmation(false, "The specified confirmation token doesn't exist"));
        } catch (RuntimeException e) {
            log.error("The account confirmation has failed", e);
            throw new RegistrationException(e);
        }
    }

    private AccountConfirmation doConfirmation(ConfirmationToken token) {
        if(token.isTokenValid()) {
            userService.enableUser(token.getUsername());
            tokenService.revokeToken(token);
            return AccountConfirmation.success();
        }
        return AccountConfirmation.failed("Invalid token");
    }

    private Mail createConfirmationMail(RegistrationRequest registrationRequest) {
        final String username = registrationRequest.getUsername();
        final String confirmationUrl = createRegistrationConfirmationUrl(username);
        final String mailContent = String.format(CONFIRMATION_MAIL_TEMPLATE, username, confirmationUrl);
        return new Mail("Registration", registrationRequest.getEmail(), mailContent);
    }

    private String createRegistrationConfirmationUrl(String username) {
        final Token registrationToken = tokenService.createAccountConfirmationToken(username);
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
