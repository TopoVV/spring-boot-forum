package com.topov.forum.controller;

import com.topov.forum.dto.LoginRequest;
import com.topov.forum.security.AuthenticationService;
import com.topov.forum.security.jwt.JwtToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        log.debug("Authenticating user {}", loginRequest);
        try {
            final JwtToken token = authenticationService.createAuthenticationToken(loginRequest);
            return ResponseEntity.status(HttpStatus.OK)
                .header(authorizationHeader, jwtPrefix.concat(token.getTokenValue()))
                .body("WELCOME");
        } catch (AuthenticationException e) {
            log.error("An unsuccessful authentication attempt detected", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Bad credentials");
        } catch (RuntimeException e) {
            log.error("An unexpected error happened during authentication");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Woops..., Something went wrong! Please, try again later");
        }
    }

}
