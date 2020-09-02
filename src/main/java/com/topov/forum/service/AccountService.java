package com.topov.forum.service;

import com.topov.forum.dto.OperationResult;
import com.topov.forum.token.AccountConfirmationToken;

public interface AccountService {
    AccountConfirmationToken createAccountConfirmationToken(String username);
    OperationResult confirmAccount(String token);
}

