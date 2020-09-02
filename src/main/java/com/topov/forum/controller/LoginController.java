package com.topov.forum.controller;

import com.topov.forum.dto.OperationResult;
import com.topov.forum.dto.OperationResultFail;
import com.topov.forum.dto.OperationResultSuccess;
import com.topov.forum.dto.request.authentication.LoginRequest;
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

    @Value("${jwt.prefix}")
    private String jwtPrefix;
    @Value("${jwt.authorizationHeader}")
    private String authorizationHeader;

    @Autowired
    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(
        value = "/auth",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OperationResult> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        log.debug("Handling POST login request. User: {}", loginRequest);
        try {
            final JwtToken token = authenticationService.authenticate(loginRequest);
            OperationResult response = OperationResultSuccess.builder()
                .httpCode(HttpStatus.OK)
                .message("Welcome")
                .data(token)
                .build();

            return ResponseEntity.status(response.getHttpCode())
                .header(authorizationHeader, jwtPrefix.concat(token.getTokenValue()))
                .body(response);
        } catch (BadCredentialsException e) {
            log.error("Bad credentials", e);
            OperationResult response = OperationResultFail.builder()
                .httpCode(HttpStatus.FORBIDDEN)
                .message("Welcome")
                .errors("Bad credentials")
                .build();
            return ResponseEntity.status(response.getHttpCode()).body(response);
        } catch (DisabledException e) {
            log.error("Account {} Disabled", loginRequest.getUsername());
            OperationResult response = OperationResultFail.builder()
                .httpCode(HttpStatus.FORBIDDEN)
                .message("Welcome")
                .errors("Account disabled")
                .build();
            return ResponseEntity.status(response.getHttpCode()).body(response);
        } catch (RuntimeException e) {
            log.error("An unexpected error happened during authentication");
            OperationResult response = OperationResultFail.builder()
                .httpCode(HttpStatus.FORBIDDEN)
                .message("Welcome")
                .errors("Unknown error")
                .build();
            return ResponseEntity.status(response.getHttpCode()).body(response);
        }
    }

}
