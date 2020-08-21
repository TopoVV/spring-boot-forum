package com.topov.forum.service;

import com.topov.forum.token.RegistrationToken;
import com.topov.forum.token.SuperuserToken;

public interface TokenService {
    RegistrationToken createRegistrationToken(String username);
    RegistrationToken getRegistrationToken(String token);
    SuperuserToken createSuperuserToken();
}
