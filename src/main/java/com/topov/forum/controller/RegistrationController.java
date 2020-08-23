package com.topov.forum.controller;

import com.topov.forum.dto.request.RegistrationRequest;
import com.topov.forum.dto.request.SuperuserRegistrationRequest;
import com.topov.forum.dto.response.AccountConfirmation;
import com.topov.forum.dto.response.OperationResponse;
import com.topov.forum.dto.response.RegistrationResponse;
import com.topov.forum.dto.response.ValidationError;
import com.topov.forum.service.RegistrationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.topov.forum.dto.request.SuperuserRegistrationRequest.SuperuserRegistrationValidationSequence;

@Log4j2
@Controller
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @ResponseBody
    @PostMapping(
        value = "/registration",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OperationResponse> regRegularUser(@Valid @RequestBody RegistrationRequest registrationRequest,
                                                            BindingResult bindingResult) {
        log.debug("Handling a registration request: {}", registrationRequest);
        if(bindingResult.hasErrors()) {
            final ValidationError response = new ValidationError(bindingResult);
            return ResponseEntity.badRequest().body(response);
        }
        final RegistrationResponse response = registrationService.registerRegularUser(registrationRequest);
        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @PostMapping(
        value = "/registration/superuser",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OperationResponse> regSuperuser(@Validated(SuperuserRegistrationValidationSequence.class)
                                                          @RequestBody SuperuserRegistrationRequest registrationRequest,
                                                          BindingResult bindingResult) {
        log.debug("Handling a superuser registration request: {}", registrationRequest);
        if(bindingResult.hasErrors()) {
            final ValidationError response = new ValidationError(bindingResult);
            return ResponseEntity.badRequest().body(response);
        }
        RegistrationResponse response = registrationService.registerSuperuser(registrationRequest);
        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @GetMapping("/registration/{token}")
    public ResponseEntity<AccountConfirmation> confirmAccountGet(@PathVariable String token) {
        final AccountConfirmation accountConfirmation = registrationService.confirmAccount(token);
        return ResponseEntity.ok(accountConfirmation);
    }
}
