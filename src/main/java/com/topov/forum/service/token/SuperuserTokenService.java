package com.topov.forum.service.token;

import com.topov.forum.token.SuperuserToken;
import net.bytebuddy.implementation.bind.annotation.Super;

public interface SuperuserTokenService {
    SuperuserToken createSuperuserToken();
    void revokeSuperuserToken(String token);
}
