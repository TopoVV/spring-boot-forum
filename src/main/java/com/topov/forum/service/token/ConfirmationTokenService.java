package com.topov.forum.service.token;

import com.topov.forum.token.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {
    ConfirmationToken createAccountConfirmationToken(String username);
    Optional<ConfirmationToken> getAccountConfirmationToken(String token);
    void revokeConfirmationToken(String token);
}
