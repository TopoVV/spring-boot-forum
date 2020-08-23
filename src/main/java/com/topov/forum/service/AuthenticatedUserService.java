package com.topov.forum.service;

import com.topov.forum.model.ForumUser;
import com.topov.forum.security.ForumUserDetails;

public interface AuthenticatedUserService {
    ForumUserDetails getAuthenticatedUser();
}
