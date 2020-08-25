package com.topov.forum.security;

import com.topov.forum.dto.request.LoginRequest;
import com.topov.forum.security.jwt.JwtToken;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    JwtToken authenticate(LoginRequest loginRequest);
    UserDetails getAuthenticatedUser();
}
