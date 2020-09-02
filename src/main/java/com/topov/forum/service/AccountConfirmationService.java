package com.topov.forum.service;

import com.topov.forum.dto.AccountConfirmationResponse;
import com.topov.forum.token.AccountConfirmationToken;
import org.springframework.transaction.annotation.Transactional;

public interface AccountConfirmationService {
    @Transactional
    AccountConfirmationToken createAccountConfirmationToken(String username);

    AccountConfirmationResponse confirmAccount(String token);
}

