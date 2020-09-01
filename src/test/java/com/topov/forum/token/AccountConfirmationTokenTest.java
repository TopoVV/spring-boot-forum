package com.topov.forum.token;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountConfirmationTokenTest {
    @Test
    void whenCreatedMoreThen5MinutesAgo_ThenReturnFalse() {
        ConfirmationToken confirmationToken = new ConfirmationToken("username");
        confirmationToken.setCreationTime(LocalDateTime.now().minusMinutes(10));
        assertFalse(confirmationToken.isTokenValid());
    }

    @Test
    void whenCreatedLessThen5MinutesAgo_ThenReturnFalse() {
        ConfirmationToken confirmationToken = new ConfirmationToken("username");
        assertTrue(confirmationToken.isTokenValid());
    }

    @Test
    void whenTokenAlreadyUsed_ThenReturnFalse() {
        ConfirmationToken confirmationToken = new ConfirmationToken("username");
        confirmationToken.setIsEnabled(false);
        assertFalse(confirmationToken.isTokenValid());
    }

}
