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
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.ValidationService;
import com.topov.forum.validation.registration.rule.RegistrationValidationRule;
import com.topov.forum.validation.registration.rule.SuperuserRegistrationValidationRule;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Log4j2
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final ValidationService validationService;
    private final SuperuserTokenService superuserTokenService;
    private final AccountService accountService;
    private final UserService userService;
    private final MailSender mailSender;

    public RegistrationServiceImpl(ValidationService validationService,
                                   SuperuserTokenService superuserTokenService,
                                   UserService userService,
                                   MailSender mailSender,
                                   AccountService accountService) {
        this.superuserTokenService = superuserTokenService;
        this.validationService = validationService;
        this.accountService = accountService;
        this.userService = userService;
        this.mailSender = mailSender;
    }

    @Transactional
    public OperationResult registerRegularUser(RegistrationRequest registrationRequest) {
        log.debug("Registration of the user {}", registrationRequest);
        try {
            final RegistrationValidationRule validationRule = new RegistrationValidationRule(registrationRequest);
            final var validationResult = validationService.validate(validationRule);
            if (validationResult.containsErrors()) {
                final List<Error> errors = validationResult.getValidationErrors();
                return new RegistrationResult(HttpStatus.BAD_REQUEST, errors, "User registration failed");
            }

            final var confirmationToken =
                accountService.createAccountConfirmationToken(registrationRequest.getUsername());

            userService.createRegularUser(registrationRequest);
            mailSender.sendAccountConfirmationMail(registrationRequest, confirmationToken.getTokenValue());

            return new RegistrationResult(HttpStatus.OK, "You've been successfully registered. To confirm your account follow the link, which was sent to your email");
        } catch (MailException e) {
            log.error("Error during sending the account confirmation email", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Confirmation email cannot be sent");
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
            final SuperuserRegistrationValidationRule validationRule =
                new SuperuserRegistrationValidationRule(registrationRequest);
            final ValidationResult validationResult = validationService.validate(validationRule);
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
