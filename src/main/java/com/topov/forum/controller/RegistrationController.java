package com.topov.forum.controller;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;
import com.topov.forum.dto.response.registration.RegistrationResponse;
import com.topov.forum.service.registration.RegistrationService;
import com.topov.forum.validation.registration.RegistrationValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public ResponseEntity<RegistrationResponse> regRegularUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        log.debug("Handling (POST) registration request: {}", registrationRequest);
        final RegistrationResponse response = registrationService.registerRegularUser(registrationRequest);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping(
        value = "/registration/superuser",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RegistrationResponse> regSuperuser(@Valid @RequestBody SuperuserRegistrationRequest registrationRequest) {
        log.debug("Handling (POST) superuser registration request: {}", registrationRequest);
        final RegistrationResponse response = registrationService.registerSuperuser(registrationRequest);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
