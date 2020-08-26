package com.topov.forum.security.jwt;

import com.topov.forum.security.ForumUserDetails;

public interface JwtService {
    JwtToken createTokenForUser(ForumUserDetails user);
    ForumUserDetails extractUser(String token);
    boolean isTokenValid(String token);
}
