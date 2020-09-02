package com.topov.forum.service.registration;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;
import com.topov.forum.email.Mail;
import com.topov.forum.email.MailSender;
import com.topov.forum.exception.RegistrationException;
import com.topov.forum.service.token.SuperuserTokenService;
import com.topov.forum.service.user.UserService;
import com.topov.forum.token.Token;
import com.topov.forum.validation.registration.RegistrationValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.MailException;
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
    private final ConfirmationTokenService confirmationTokenService;
    private final SuperuserTokenService superuserTokenService;

    public RegistrationServiceImpl(ConfirmationTokenService confirmationTokenService,
                                   SuperuserTokenService superuserTokenService,
                                   RegistrationValidator registrationValidator,
                                   UserService userService,
                                   MailSender mailSender) {
        this.userService = userService;
        this.mailSender = mailSender;
        this.confirmationTokenService = confirmationTokenService;
        this.superuserTokenService = superuserTokenService;
    }

    @Transactional
    public void registerRegularUser(RegistrationRequest registrationRequest) {
        log.debug("Registration of the user {}", registrationRequest);
        try {
            final Mail mail = createConfirmationMail(registrationRequest);
            userService.createRegularUser(registrationRequest);
            mailSender.sendMail(mail);
//            return RegistrationResult.regularUserRegistrationSuccess(mail.getRecipient());
        } catch (MailException e) {
            log.error("Error during sending the account confirmation email", e);
            throw new RegistrationException("Cannot register the user. Failed to send the account confirmation mail", e);
        } catch(RuntimeException e) {
            log.error("Error during registration", e);
            throw new RegistrationException("Cannot register the user. Please, try again later", e);
        }
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

    @Override
    @Transactional
    public void registerSuperuser(SuperuserRegistrationRequest registrationRequest) {
        log.debug("Superuser registration");
        try {
            superuserTokenService.revokeSuperuserToken(registrationRequest.getToken());
            userService.createSuperuser(registrationRequest);
//            return RegistrationResult.superuserRegistrationSuccess();
        } catch(RuntimeException e) {
            log.error("Error during registration", e);
            throw new RegistrationException("Cannot register the superuser. Please, try again later", e);
        }
    }
}
