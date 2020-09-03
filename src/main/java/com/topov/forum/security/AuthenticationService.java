package com.topov.forum.security;

import com.topov.forum.dto.request.authentication.LoginRequest;
<<<<<<< HEAD
import com.topov.forum.dto.result.authentication.AuthenticationResult;

public interface AuthenticationService {
    AuthenticationResult authenticate(LoginRequest loginRequest);
=======
import com.topov.forum.security.jwt.JwtToken;

public interface AuthenticationService {
    JwtToken authenticate(LoginRequest loginRequest);
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
    ForumUserDetails getAuthenticatedUser();
    Long getCurrentUserId();
}
