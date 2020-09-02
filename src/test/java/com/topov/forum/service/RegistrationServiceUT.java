package com.topov.forum.service;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.email.Mail;
import com.topov.forum.email.MailSender;
import com.topov.forum.email.MailSenderImpl;
import com.topov.forum.exception.RegistrationException;
import com.topov.forum.service.registration.RegistrationService;
import com.topov.forum.service.token.ConfirmationTokenService;
import com.topov.forum.service.token.ConfirmationTokenServiceImpl;
import com.topov.forum.service.user.UserService;
import com.topov.forum.service.user.UserServiceImpl;
import com.topov.forum.token.ConfirmationToken;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.mock.mockito.SpyBeans;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@MockBeans({ @MockBean(UserServiceImpl.class), @MockBean(ConfirmationTokenServiceImpl.class) })
@SpyBeans({ @SpyBean(MailSenderImpl.class) })
class RegistrationServiceUT {
    private final RegistrationService registrationService;
    private final MailSender mailSender;
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    RegistrationServiceUT(RegistrationService registrationService,
                          MailSender mailSender,
                          UserService userService,
                          ConfirmationTokenService confirmationTokenService) {
        this.registrationService = registrationService;
        this.mailSender = mailSender;
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
    }

    @Test void whenUserServiceThrows_ThenRethrowException() {
        final RegistrationRequest registrationRequestMock = mock(RegistrationRequest.class);
        when(registrationRequestMock.getUsername()).thenReturn("username");
        when(registrationRequestMock.getEmail()).thenReturn("email@email.ru");

//        doThrow(DataIntegrityViolationException.class).when(userService).createRegularUser(registrationRequestMock);

        assertThrows(RuntimeException.class, () -> registrationService.registerRegularUser(registrationRequestMock));
        verifyNoInteractions(mailSender);
    }

    @Test
    void whenCreateUser_ThenSendEmail() {
        final RegistrationRequest registrationRequestMock = mock(RegistrationRequest.class);
        when(registrationRequestMock.getUsername()).thenReturn("username");
        when(registrationRequestMock.getEmail()).thenReturn("email@email.ru");


        final ConfirmationToken confirmationTokenMock = mock(ConfirmationToken.class);
        when(confirmationTokenMock.getTokenValue()).thenReturn("123456789");

        when(confirmationTokenService.createAccountConfirmationToken(any())).thenReturn(confirmationTokenMock);

        registrationService.registerRegularUser(registrationRequestMock);

        final ArgumentCaptor<Mail> email = ArgumentCaptor.forClass(Mail.class);

        verify(mailSender).sendMail(email.capture());
        assertEquals(registrationRequestMock.getEmail(), email.getValue().getRecipient());
        assertTrue(email.getValue().getContent().contains("123456789"));
    }

    @Test
    void whenConfirmAccountUserNotFound_ThenThrowRegistrationException() {
        final ConfirmationToken confirmationTokenMock = mock(ConfirmationToken.class);
        when(confirmationTokenMock.isTokenValid()).thenReturn(true);
        when(confirmationTokenMock.getUsername()).thenReturn("username");

        when(confirmationTokenService.getAccountConfirmationToken("token")).thenReturn(Optional.of(confirmationTokenMock));
        doThrow(RuntimeException.class).when(userService).enableUser("username");

//        assertThrows(RegistrationException.class, () -> registrationService.confirmAccount("token"));
    }
}
