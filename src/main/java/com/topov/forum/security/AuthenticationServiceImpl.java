package com.topov.forum.security;

import com.topov.forum.dto.LoginRequest;
import com.topov.forum.security.jwt.JwtService;
import com.topov.forum.security.jwt.JwtToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

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
    public JwtToken createAuthenticationToken(LoginRequest loginRequest) {
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
