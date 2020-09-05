package com.topov.forum.service.account;

import com.topov.forum.dto.result.OperationResult;
import com.topov.forum.token.AccountConfirmationToken;

public interface AccountService {
    AccountConfirmationToken createAccountConfirmationToken(String username);
    OperationResult confirmAccount(String token);
}

