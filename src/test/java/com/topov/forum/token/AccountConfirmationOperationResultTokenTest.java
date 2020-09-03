package com.topov.forum.token;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountConfirmationOperationResultTokenTest {
    @Test
    void whenCreatedMoreThen5MinutesAgo_ThenReturnFalse() {
        AccountConfirmationToken accountConfirmationToken = new AccountConfirmationToken("username");
        accountConfirmationToken.setCreationTime(LocalDateTime.now().minusMinutes(10));
        assertFalse(accountConfirmationToken.isTokenValid());
    }

    @Test
    void whenCreatedLessThen5MinutesAgo_ThenReturnFalse() {
        AccountConfirmationToken accountConfirmationToken = new AccountConfirmationToken("username");
        assertTrue(accountConfirmationToken.isTokenValid());
    }

    @Test
    void whenTokenAlreadyUsed_ThenReturnFalse() {
        AccountConfirmationToken accountConfirmationToken = new AccountConfirmationToken("username");
        accountConfirmationToken.setIsEnabled(false);
        assertFalse(accountConfirmationToken.isTokenValid());
    }

}
