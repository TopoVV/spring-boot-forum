package com.topov.forum.security;

import com.topov.forum.dto.LoginRequest;
import com.topov.forum.security.jwt.JwtToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    JwtToken createAuthenticationToken(LoginRequest loginRequest);
    UserDetails getAuthenticatedUser();
}
