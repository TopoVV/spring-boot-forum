package com.topov.forum.service;

import com.topov.forum.token.RegistrationToken;

public interface TokenService {
    RegistrationToken createRegistrationToken(String username);
}
