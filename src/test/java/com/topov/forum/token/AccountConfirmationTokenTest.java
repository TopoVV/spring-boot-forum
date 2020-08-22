package com.topov.forum.token;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AccountConfirmationTokenTest {
    @Test
    public void whenCreatedMoreThen5MinutesAgo_ThenReturnFalse() {
        ConfirmationToken confirmationToken = new ConfirmationToken("username");
        confirmationToken.setCreationTime(LocalDateTime.now().minusMinutes(10));
        assertFalse(confirmationToken.isTokenValid());
    }
    @Test
    public void whenCreatedLessThen5MinutesAgo_ThenReturnFalse() {
        ConfirmationToken confirmationToken = new ConfirmationToken("username");
        assertTrue(confirmationToken.isTokenValid());
    }
    @Test
    public void whenTokenAlreadyUsed_ThenReturnFalse() {
        ConfirmationToken confirmationToken = new ConfirmationToken("username");
        confirmationToken.setIsEnabled(false);
        assertFalse(confirmationToken.isTokenValid());
    }

}
