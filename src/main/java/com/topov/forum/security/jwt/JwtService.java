package com.topov.forum.security.jwt;

import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Role;
import com.topov.forum.security.ForumUserDetails;

import java.util.Set;

public interface JwtService {
    JwtToken createTokenForUser(ForumUserDetails user);
    ForumUserDetails extractUser(String token);
    boolean isTokenValid(String token);
}
