package com.topov.forum.service.token;

import com.topov.forum.token.SuperuserToken;

public interface SuperuserTokenService {
    SuperuserToken createSuperuserToken();
    boolean isSuperuserTokenValid(String token);
    void revokeSuperuserToken(String token);
}
