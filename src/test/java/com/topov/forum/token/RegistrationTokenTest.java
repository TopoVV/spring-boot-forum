package com.topov.forum.token;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationTokenTest {
    @Test
    public void whenCreatedMoreThen5MinutesAgo_ThenReturnFalse() {
        RegistrationToken registrationToken = new RegistrationToken("username");
        registrationToken.setCreationTime(LocalDateTime.now().minusMinutes(10));
        assertFalse(registrationToken.isTokenValid());
    }
    @Test
    public void whenCreatedLessThen5MinutesAgo_ThenReturnFalse() {
        RegistrationToken registrationToken = new RegistrationToken("username");
        assertTrue(registrationToken.isTokenValid());
    }
    @Test
    public void whenTokenAlreadyUsed_ThenReturnFalse() {
        RegistrationToken registrationToken = new RegistrationToken("username");
        registrationToken.setIsUsed(true);
        assertFalse(registrationToken.isTokenValid());
    }

}
