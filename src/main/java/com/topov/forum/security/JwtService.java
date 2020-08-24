package com.topov.forum.security;

import com.topov.forum.model.ForumUser;

public interface JwtService {
    JwtToken createTokenForUser(ForumUserDetails user);
}
