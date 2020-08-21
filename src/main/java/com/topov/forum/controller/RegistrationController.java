package com.topov.forum.controller;

import com.topov.forum.dto.request.RegistrationRequest;
import com.topov.forum.dto.response.RegistrationResponse;
import com.topov.forum.service.RegistrationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<RegistrationResponse> registrationPost(@Valid @RequestBody RegistrationRequest registrationRequest,
                                                                 BindingResult bindingResult) {
        log.debug("Handling registration request: {}", registrationRequest);

        if(bindingResult.hasErrors()) {
            final var response = new RegistrationResponse("Cannot perform registration! Invalid input", bindingResult);
            return ResponseEntity.badRequest().body(response);
        }

        registrationService.registerUser(registrationRequest);
        return ResponseEntity.ok(new RegistrationResponse("You've been successfully registered!"));
    }

    @ResponseBody
    @GetMapping("/registration/{token}")
    public ResponseEntity<String> confirmRegistrationGet(@PathVariable String token) {
        registrationService.confirmRegistration(token);
        return ResponseEntity.ok("Registration confirmed");
    }
}
