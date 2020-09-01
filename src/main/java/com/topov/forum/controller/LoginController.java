package com.topov.forum.controller;

import com.topov.forum.dto.request.authentication.LoginRequest;
import com.topov.forum.dto.response.InvalidInputResponse;
import com.topov.forum.dto.response.OperationResponse;
import com.topov.forum.dto.response.authentication.LoginResponse;
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
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<OperationResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        log.debug("Handling POST login request. User: {}", loginRequest);
        try {
            final JwtToken token = authenticationService.authenticate(loginRequest);
            return ResponseEntity.status(HttpStatus.OK)
                .header(authorizationHeader, jwtPrefix.concat(token.getTokenValue()))
                .body(LoginResponse.success(loginRequest.getUsername()));
        } catch (BadCredentialsException e) {
            log.error("Bad credentials", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(LoginResponse.badCredentials());
        } catch (DisabledException e) {
            log.error("Account {} Disabled", loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(LoginResponse.accountNotEnabled());
        } catch (RuntimeException e) {
            log.error("An unexpected error happened during authentication");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(LoginResponse.unknownError());
        }
    }

}
