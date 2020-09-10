package com.topov.forum.service.token;

import com.topov.forum.token.AccountConfirmationToken;

public interface AccountConfirmationTokenService {
    AccountConfirmationToken createConfirmationToken(String username);
    AccountConfirmationToken getConfirmationToken(String tokenValue);
    void revokeConfirmationToken(String token);
}
