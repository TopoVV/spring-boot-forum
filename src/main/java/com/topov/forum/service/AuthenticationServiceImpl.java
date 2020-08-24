package com.topov.forum.service;

import com.topov.forum.dto.LoginRequest;
import com.topov.forum.model.ForumUser;
import com.topov.forum.security.ForumUserDetails;
import com.topov.forum.security.JwtService;
import com.topov.forum.security.JwtToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Log4j2
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public JwtToken authenticate(LoginRequest loginRequest) {
        try {
            final String username = loginRequest.getUsername();
            final String password = loginRequest.getPassword();
            final var authToken = new UsernamePasswordAuthenticationToken(username, password);
            final Authentication authentication = authenticationManager.authenticate(authToken);
            return jwtService.createTokenForUser((ForumUserDetails) authentication.getPrincipal());
        } catch (AuthenticationException e) {
            log.error("An unexpected error happened during authentication");
            throw e;
        }
    }

    @Override
    public ForumUserDetails getAuthenticatedUser() {
        return (ForumUserDetails) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();
    }
}
