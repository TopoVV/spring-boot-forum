package com.topov.forum.security;

import com.topov.forum.dto.request.authentication.LoginRequest;
import com.topov.forum.security.jwt.JwtService;
import com.topov.forum.security.jwt.JwtToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public JwtToken authenticate(LoginRequest loginRequest) {
        log.debug("Authentication user: {}", loginRequest);
        final String username = loginRequest.getUsername();
        final String password = loginRequest.getPassword();
        final var authToken = new UsernamePasswordAuthenticationToken(username, password);

        final Authentication authentication = authenticationManager.authenticate(authToken);
        return jwtService.createTokenForUser((ForumUserDetails) authentication.getPrincipal());
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
