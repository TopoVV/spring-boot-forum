package com.topov.forum.token;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationTokenTest {
    @Test
    public void whenCreatedMoreThen5MinutesAgo_ThenReturnFalse() {
        RegistrationToken registrationToken = new RegistrationToken("username");
        registrationToken.setCreationTime(LocalDateTime.of(2020, 8, 21, 15, 10));
        assertFalse(registrationToken.confirmRegistration());
    }
    @Test
    public void whenCreatedLessThen5MinutesAgo_ThenReturnFalse() {
        RegistrationToken registrationToken = new RegistrationToken("username");
        assertTrue(registrationToken.confirmRegistration());
    }
    @Test
    public void whenTokenAlreadyUsed_ThenReturnFalse() {
        RegistrationToken registrationToken = new RegistrationToken("username");
        registrationToken.setIsUsed(true);
        assertFalse(registrationToken.confirmRegistration());
    }

}
