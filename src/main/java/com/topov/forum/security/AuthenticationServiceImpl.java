package com.topov.forum.security;

<<<<<<< HEAD
import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.request.authentication.LoginRequest;
import com.topov.forum.dto.result.OperationResult;
import com.topov.forum.dto.result.authentication.AuthenticationResult;
=======
import com.topov.forum.dto.request.authentication.LoginRequest;
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
import com.topov.forum.security.jwt.JwtService;
import com.topov.forum.security.jwt.JwtToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
=======
import org.springframework.security.authentication.AuthenticationManager;
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
@Log4j2
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

<<<<<<< HEAD
    @Value("${jwt.prefix}")
    private String jwtPrefix;
    @Value("${jwt.authorizationHeader}")
    private String authorizationHeader;

=======
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
<<<<<<< HEAD
    public AuthenticationResult authenticate(LoginRequest loginRequest) {
        log.debug("Authentication user: {}", loginRequest);

        try {
            final String username = loginRequest.getUsername();
            final String password = loginRequest.getPassword();
            final var authToken = new UsernamePasswordAuthenticationToken(username, password);

            final Authentication authentication = authenticationManager.authenticate(authToken);
            final JwtToken jwt = jwtService.createTokenForUser((ForumUserDetails) authentication.getPrincipal());
            return new AuthenticationResult(HttpStatus.OK, "Welcome", jwt ,this.authorizationHeader, this.jwtPrefix);
        } catch (BadCredentialsException e) {
            log.error("Bad credentials", e);
            final Error badCredentials = new Error("Bad credentials");
            return new AuthenticationResult(HttpStatus.FORBIDDEN, badCredentials, "Not authenticated");
        } catch (DisabledException e) {
            log.error("Account {} Disabled", loginRequest.getUsername());
            final Error accountDisabled = new Error("Account disabled");
            return new AuthenticationResult(HttpStatus.FORBIDDEN, accountDisabled, "Not authenticated");
        } catch (RuntimeException e) {
            log.error("An unexpected error happened during authentication");
            final Error unexpectedError = new Error("An unexpected error happened during authentication");
            return new AuthenticationResult(HttpStatus.FORBIDDEN, unexpectedError, "Not authenticated");
        }
=======
    public JwtToken authenticate(LoginRequest loginRequest) {
        log.debug("Authentication user: {}", loginRequest);
        final String username = loginRequest.getUsername();
        final String password = loginRequest.getPassword();
        final var authToken = new UsernamePasswordAuthenticationToken(username, password);

        final Authentication authentication = authenticationManager.authenticate(authToken);
        return jwtService.createTokenForUser((ForumUserDetails) authentication.getPrincipal());
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
    }

    @Override
    public ForumUserDetails getAuthenticatedUser() {
        log.debug("Getting current user");
        return (ForumUserDetails) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();
    }

    @Override
    public Long getCurrentUserId() {
        final ForumUserDetails principal = (ForumUserDetails) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();
        return principal.getUserId();
    }
}
