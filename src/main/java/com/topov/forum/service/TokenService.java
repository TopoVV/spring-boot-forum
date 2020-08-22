package com.topov.forum.service;

import com.topov.forum.token.ConfirmationToken;
import com.topov.forum.token.SuperuserToken;
import com.topov.forum.token.Token;

import java.util.Optional;

public interface TokenService {
    ConfirmationToken createAccountConfirmationToken(String username);
    Optional<ConfirmationToken> getAccountConfirmationToken(String token);
    SuperuserToken createSuperuserToken();
    SuperuserToken getSuperuserToken(String token);
    void revokeToken(Token token);

}
