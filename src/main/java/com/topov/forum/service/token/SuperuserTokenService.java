package com.topov.forum.service.token;

import com.topov.forum.token.SuperuserToken;

public interface SuperuserTokenService {
    SuperuserToken createSuperuserToken();
    void revokeSuperuserToken(String token);
}
