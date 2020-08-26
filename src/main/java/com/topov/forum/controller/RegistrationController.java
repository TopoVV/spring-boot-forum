package com.topov.forum.controller;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;
import com.topov.forum.dto.response.registration.AccountConfirmation;
import com.topov.forum.dto.response.OperationResponse;
import com.topov.forum.dto.response.registration.RegistrationResponse;
import com.topov.forum.dto.response.ValidationError;
import com.topov.forum.service.registration.RegistrationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.topov.forum.dto.request.registration.SuperuserRegistrationRequest.SuperuserRegistrationValidation;

@Log4j2
@RestController
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(
        value = "/registration",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OperationResponse> regRegularUser(@Valid @RequestBody RegistrationRequest registrationRequest,
                                                            BindingResult bindingResult) {
        log.debug("Handling (POST) registration request: {}", registrationRequest);
        if(bindingResult.hasErrors()) {
            final ValidationError validationError = new ValidationError(bindingResult);
            return ResponseEntity.badRequest().body(validationError);
        }
        final RegistrationResponse response = registrationService.registerRegularUser(registrationRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping(
        value = "/registration/superuser",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OperationResponse> regSuperuser(@Validated(SuperuserRegistrationValidation.class)
                                                          @RequestBody SuperuserRegistrationRequest registrationRequest,
                                                          BindingResult bindingResult) {
        log.debug("Handling (POST) superuser registration request: {}", registrationRequest);
        if(bindingResult.hasErrors()) {
            final ValidationError validationError = new ValidationError(bindingResult);
            return ResponseEntity.badRequest().body(validationError);
        }
        RegistrationResponse response = registrationService.registerSuperuser(registrationRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/registration/{token}")
    public ResponseEntity<OperationResponse> confirmAccountGet(@PathVariable String token) {
        log.debug("Handling (GET) account confirmation request");
        final AccountConfirmation accountConfirmation = registrationService.confirmAccount(token);
        return ResponseEntity.ok(accountConfirmation);
    }
}
