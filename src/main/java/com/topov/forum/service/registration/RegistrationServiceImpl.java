package com.topov.forum.service.registration;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;
import com.topov.forum.dto.response.registration.RegistrationResponse;
import com.topov.forum.mail.MailSender;
import com.topov.forum.exception.RegistrationException;
import com.topov.forum.service.AccountConfirmationService;
import com.topov.forum.service.token.SuperuserTokenService;
import com.topov.forum.service.user.UserService;
import com.topov.forum.dto.ValidationErrors;
import com.topov.forum.validation.registration.RegistrationValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final UserService userService;
    private final MailSender mailSender;
    private final RegistrationValidator registrationValidator;
    private final SuperuserTokenService superuserTokenService;
    private final AccountConfirmationService accountConfirmationService;

    public RegistrationServiceImpl(SuperuserTokenService superuserTokenService,
                                   UserService userService,
                                   MailSender mailSender,
                                   RegistrationValidator registrationValidator,
                                   AccountConfirmationService accountConfirmationService) {
        this.userService = userService;
        this.mailSender = mailSender;
        this.superuserTokenService = superuserTokenService;
        this.registrationValidator = registrationValidator;
        this.accountConfirmationService = accountConfirmationService;
    }

    @Transactional
    public RegistrationResponse registerRegularUser(RegistrationRequest registrationRequest) {
        log.debug("Registration of the user {}", registrationRequest);
        try {
            final var validationResult = registrationValidator.validateRegularUserRegistration(registrationRequest);
            if (validationResult.isValid()) {
                final ValidationErrors validationErrors = new ValidationErrors(validationResult.getValidationErrors());
                return RegistrationResponse.builder()
                    .code(HttpStatus.BAD_REQUEST)
                    .message("User registration failed")
                    .errors(validationErrors)
                    .build();
            }

            final var accountConfirmationToken =
                accountConfirmationService.createAccountConfirmationToken(registrationRequest.getUsername());

            userService.createRegularUser(registrationRequest);
            mailSender.sendAccountConfirmationMail(registrationRequest, accountConfirmationToken.getTokenValue());

            return RegistrationResponse.builder()
                .code(HttpStatus.OK)
                .message("You've been successfully registered")
                .build();

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
            final var validationResult = registrationValidator.validateSuperuserRegistration(registrationRequest);
            if (validationResult.isValid()) {
                final ValidationErrors validationErrors = new ValidationErrors(validationResult.getValidationErrors());
                return RegistrationResponse.builder()
                    .code(HttpStatus.BAD_REQUEST)
                    .message("Superuser registration failed")
                    .errors(validationErrors)
                    .build();
            }

            superuserTokenService.revokeSuperuserToken(registrationRequest.getToken());
            userService.createSuperuser(registrationRequest);

            return RegistrationResponse.builder()
                .code(HttpStatus.OK)
                .message("You've been successfully registered")
                .build();

        } catch(RuntimeException e) {
            log.error("Error during registration", e);
            throw new RegistrationException("Cannot register the superuser. Please, try again later", e);
        }
    }
}
