package com.topov.forum.controller;

import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.result.OperationResult;
import com.topov.forum.dto.request.authentication.LoginRequest;
import com.topov.forum.dto.result.authentication.AuthenticationResult;
import com.topov.forum.security.AuthenticationService;
import com.topov.forum.security.jwt.JwtToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RestController
public class LoginController {
    private final AuthenticationService authenticationService;

    @Autowired
    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(
        value = "/auth",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        log.debug("Handling POST login request. User: {}", loginRequest);
        final AuthenticationResult authenticationResult = authenticationService.authenticate(loginRequest);
        return authenticationResult.createResponseEntity();
    }

}
