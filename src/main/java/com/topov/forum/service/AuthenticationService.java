package com.topov.forum.service;

import com.topov.forum.dto.LoginRequest;
import com.topov.forum.model.ForumUser;
import com.topov.forum.security.JwtToken;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    JwtToken authenticate(LoginRequest loginRequest);
    UserDetails getAuthenticatedUser();
}
