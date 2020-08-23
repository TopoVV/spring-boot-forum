package com.topov.forum.service.token;

import com.topov.forum.token.SuperuserToken;
import org.springframework.transaction.annotation.Transactional;

public interface SuperuserTokenService {
    SuperuserToken createSuperuserToken();
    boolean checkSuperuserToken(String token);
    void revokeSuperuserToken(String token);
}
