package com.topov.forum.security;

import com.topov.forum.dto.request.authentication.LoginRequest;
import com.topov.forum.dto.result.authentication.AuthenticationResult;

public interface AuthenticationService {
    AuthenticationResult authenticate(LoginRequest loginRequest);
    ForumUserDetails getAuthenticatedUser();
    Long getCurrentUserId();
}
