package com.topov.forum.service.registration;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;
import com.topov.forum.dto.result.OperationResult;
import com.topov.forum.dto.result.registration.RegistrationResult;
import com.topov.forum.exception.RegistrationException;
import com.topov.forum.mail.MailSender;
import com.topov.forum.service.account.AccountService;
import com.topov.forum.service.token.SuperuserTokenService;
import com.topov.forum.service.user.UserService;
import com.topov.forum.validation.registration.RegistrationValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final UserService userService;
    private final MailSender mailSender;
    private final RegistrationValidator registrationValidator;
    private final SuperuserTokenService superuserTokenService;
    private final AccountService accountService;

    public RegistrationServiceImpl(SuperuserTokenService superuserTokenService,
                                   UserService userService,
                                   MailSender mailSender,
                                   RegistrationValidator registrationValidator,
                                   AccountService accountService) {
        this.userService = userService;
        this.mailSender = mailSender;
        this.superuserTokenService = superuserTokenService;
        this.registrationValidator = registrationValidator;
        this.accountService = accountService;
    }

    @Transactional
    public OperationResult registerRegularUser(RegistrationRequest registrationRequest) {
        log.debug("Registration of the user {}", registrationRequest);
        try {
            final var validationResult = registrationValidator.validateRegularUserRegistration(registrationRequest);
            if (validationResult.containsErrors()) {
                final List<Error> errors = validationResult.getValidationErrors();
                return new RegistrationResult(HttpStatus.BAD_REQUEST, errors, "User registration failed");
            }

            final var accountConfirmationToken =
                accountService.createAccountConfirmationToken(registrationRequest.getUsername());

            userService.createRegularUser(registrationRequest);
            mailSender.sendAccountConfirmationMail(registrationRequest, accountConfirmationToken.getTokenValue());

            return new RegistrationResult(HttpStatus.OK, "You've been successfully registered. To confirm your account follow the link, which was sent to your email");

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
    public OperationResult registerSuperuser(SuperuserRegistrationRequest registrationRequest) {
        log.debug("Superuser registration");
        try {
            final var validationResult = registrationValidator.validateSuperuserRegistration(registrationRequest);
            if (validationResult.containsErrors()) {
                final List<Error> errors = validationResult.getValidationErrors();
                return new RegistrationResult(HttpStatus.BAD_REQUEST, errors, "Superuser registration failed");
            }

            superuserTokenService.revokeSuperuserToken(registrationRequest.getToken());
            userService.createSuperuser(registrationRequest);

            return new RegistrationResult(HttpStatus.OK, "You've been successfully registered");

        } catch(RuntimeException e) {
            log.error("Error during registration", e);
            throw new RegistrationException("Cannot register the superuser. Please, try again later", e);
        }
    }
}
