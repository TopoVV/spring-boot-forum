package com.topov.forum.service.token;

import com.topov.forum.token.SuperuserToken;

public interface SuperuserTokenService {
    SuperuserToken createSuperuserToken();
    boolean checkSuperuserToken(String token);
    void revokeSuperuserToken(String token);
}
