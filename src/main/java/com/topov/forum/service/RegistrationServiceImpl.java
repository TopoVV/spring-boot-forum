package com.topov.forum.service;

import com.topov.forum.dto.request.RegistrationRequest;
import com.topov.forum.dto.request.SuperuserRegistrationRequest;
import com.topov.forum.dto.response.AccountConfirmation;
import com.topov.forum.dto.response.RegistrationResponse;
import com.topov.forum.email.Mail;
import com.topov.forum.email.MailSender;
import com.topov.forum.exception.RegistrationException;
import com.topov.forum.service.token.ConfirmationTokenService;
import com.topov.forum.service.token.SuperuserTokenService;
import com.topov.forum.token.ConfirmationToken;
import com.topov.forum.token.Token;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Log4j2
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private static final String SUCCESSFUL_REGISTRATION_TEMPLATE = "You've been successfully registered! " +
        "Please, confirm your account by clicking the link that was sent to %s";
    private static final String CONFIRMATION_MAIL_TEMPLATE = "Welcome to Forum, %s. " +
        "To confirm your account follow this link: %s";

    private final UserService userService;
    private final MailSender mailSender;
    private final ConfirmationTokenService confirmationTokenService;
    private final SuperuserTokenService superuserTokenService;

    public RegistrationServiceImpl(ConfirmationTokenService confirmationTokenService,
                                   SuperuserTokenService superuserTokenService,
                                   UserService userService,
                                   MailSender mailSender) {
        this.userService = userService;
        this.mailSender = mailSender;
        this.confirmationTokenService = confirmationTokenService;
        this.superuserTokenService = superuserTokenService;
    }

    @Transactional
    public RegistrationResponse registerRegularUser(RegistrationRequest registrationRequest) {
        log.debug("Registration of the user {}", registrationRequest);
        try {
            final Mail mail = createConfirmationMail(registrationRequest);
            userService.createRegularUser(registrationRequest);
            mailSender.sendMail(mail);
            return new RegistrationResponse(String.format(SUCCESSFUL_REGISTRATION_TEMPLATE, mail.getRecipient()));
        } catch (MailException e) {
            log.error("Error during sending the account confirmation email", e);
            throw new RegistrationException("Cannot register the user. Failed to send the account confirmation mail", e);
        } catch(RuntimeException e) {
            log.error("Error during registration", e);
            throw new RegistrationException("Cannot register the user. Please, try again later", e);
        }
    }

    @Override
    @Transactional
    public RegistrationResponse registerSuperuser(SuperuserRegistrationRequest registrationRequest) {
        log.debug("Superuser registration");
        try {
            if(superuserTokenService.checkSuperuserToken(registrationRequest.getToken())) {
                superuserTokenService.revokeSuperuserToken(registrationRequest.getToken());
                userService.createSuperuser(registrationRequest);
               return new RegistrationResponse("The superuser has been successfully registered");
            }
           return new RegistrationResponse("Invalid token");
        } catch(RuntimeException e) {
            log.error("Error during registration", e);
            throw new RegistrationException("Cannot register the superuser. Please, try again later", e);
        }
    }

    @Override
    @Transactional
    public AccountConfirmation confirmAccount(String token) {
        log.debug("Confirmation of the account");
        try {
            return confirmationTokenService.getAccountConfirmationToken(token)
                .map(this::doConfirmation)
                .orElse(AccountConfirmation.failed("The specified confirmation token doesn't exist"));
        } catch (RuntimeException e) {
            log.error("Error during the account confirmation", e);
            throw new RegistrationException(String.format("Account confirmation failed! %s", e.getMessage()), e);
        }
    }

    private AccountConfirmation doConfirmation(ConfirmationToken token) {
        if(token.isTokenValid()) {
            userService.enableUser(token.getUsername());
            confirmationTokenService.revokeConfirmationToken(token.getTokenValue());
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
        final Token registrationToken = confirmationTokenService.createAccountConfirmationToken(username);
        final String tokenConfirmationPath = String.format("registration/%s", registrationToken.getTokenValue());
        return UriComponentsBuilder.newInstance()
                                   .scheme("http")
                                   .host("localhost")
                                   .port("8080")
                                   .path(tokenConfirmationPath)
                                   .build()
                                   .toString();
    }
}
