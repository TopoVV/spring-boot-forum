package com.topov.forum.security;

import com.topov.forum.dto.request.authentication.LoginRequest;
import com.topov.forum.security.jwt.JwtToken;

public interface AuthenticationService {
    JwtToken authenticate(LoginRequest loginRequest);
    ForumUserDetails getAuthenticatedUser();
    Long getCurrentUserId();
}
