package com.topov.forum.service;

import com.topov.forum.dto.AccountConfirmationResponse;

public interface AccountConfirmationService {
    AccountConfirmationResponse confirmAccount(String token);
}

