package com.topov.forum.service;

import com.topov.forum.dto.request.RegistrationRequest;
import com.topov.forum.email.Mail;
import com.topov.forum.email.MailSender;
import com.topov.forum.email.MailSenderImpl;
import com.topov.forum.model.ForumUser;
import com.topov.forum.token.RegistrationToken;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.mock.mockito.SpyBeans;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@MockBeans({
    @MockBean(UserServiceImpl.class),
    @MockBean(TokenServiceImpl.class)
})
@SpyBeans({
    @SpyBean(MailSenderImpl.class)
})
class RegistrationServiceImplTest {
    private final RegistrationService registrationService;
    private final MailSender mailSender;
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    RegistrationServiceImplTest(RegistrationService registrationService,
                                MailSender mailSender,
                                UserService userService,
                                TokenService tokenService) {
        this.registrationService = registrationService;
        this.mailSender = mailSender;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Test
    public void whenUserServiceThrows_ThenRethrowException() {
        final RegistrationRequest registrationRequestMock = mock(RegistrationRequest.class);
        when(registrationRequestMock.getUsername()).thenReturn("username");
        when(registrationRequestMock.getEmail()).thenReturn("email@email.ru");

        final ForumUser forumUser = new ForumUser(registrationRequestMock);
        doThrow(DataIntegrityViolationException.class).when(userService).saveRegularUser(forumUser);

        assertThrows(RuntimeException.class, () -> registrationService.registerUser(registrationRequestMock));
        verifyNoInteractions(mailSender);
    }

    @Test
    public void whenCreateUser_ThenSendEmail() {
        final RegistrationRequest registrationRequestMock = mock(RegistrationRequest.class);
        when(registrationRequestMock.getUsername()).thenReturn("username");
        when(registrationRequestMock.getEmail()).thenReturn("email@email.ru");

        final RegistrationToken registrationTokenMock = mock(RegistrationToken.class);
        when(registrationTokenMock.getToken()).thenReturn("123456789");

        when(tokenService.createRegistrationToken(any())).thenReturn(registrationTokenMock);

        registrationService.registerUser(registrationRequestMock);

        final ArgumentCaptor<Mail> email = ArgumentCaptor.forClass(Mail.class);

        verify(mailSender).sendMail(email.capture());
        assertEquals(registrationRequestMock.getEmail(), email.getValue().getRecipient());
        assertTrue(email.getValue().getContent().contains("123456789"));
    }
}
