package com.topov.forum.token;

import com.topov.forum.validation.accout.rule.ConfirmationTokenValidationRule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountConfirmationOperationResultTokenTest {
    @Test
    void whenCreatedMoreThen5MinutesAgo_ThenReturnFalse() {
        AccountConfirmationToken confirmationTokenValidationRule = new AccountConfirmationToken("username");
        confirmationTokenValidationRule.setCreationTime(LocalDateTime.now().minusMinutes(10));
        assertFalse(confirmationTokenValidationRule.isTokenValid());
    }

    @Test
    void whenCreatedLessThen5MinutesAgo_ThenReturnFalse() {
        AccountConfirmationToken confirmationToken = new AccountConfirmationToken("username");
        assertTrue(confirmationToken.isTokenValid());
    }

    @Test
    void whenTokenAlreadyUsed_ThenReturnFalse() {
        AccountConfirmationToken confirmationToken = new AccountConfirmationToken("username");
        confirmationToken.setIsEnabled(false);
        assertFalse(confirmationToken.isTokenValid());
    }

}
