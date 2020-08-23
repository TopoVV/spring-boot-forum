package com.topov.forum.service;

import com.topov.forum.model.ForumUser;
import com.topov.forum.security.ForumUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {
    @Override
    public ForumUserDetails getAuthenticatedUser() {
        final Object principal = SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();
        return (ForumUserDetails) principal;
    }
}
