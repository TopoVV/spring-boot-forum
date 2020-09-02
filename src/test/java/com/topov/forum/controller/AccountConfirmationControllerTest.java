package com.topov.forum.controller;

import com.topov.forum.service.AccountConfirmationService;
import com.topov.forum.service.AccountConfirmationServiceImpl;
import com.topov.forum.service.token.ConfirmationTokenService;
import com.topov.forum.service.token.ConfirmationTokenServiceImpl;
import com.topov.forum.service.user.UserService;
import com.topov.forum.service.user.UserServiceImpl;
import com.topov.forum.token.ConfirmationToken;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.xml.bind.annotation.W3CDomHandler;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@MockBeans({
    @MockBean(ConfirmationTokenService.class),
    @MockBean(UserService.class)
})
class AccountConfirmationControllerTest {
    private final ConfirmationTokenService confirmationService;
    private final UserService userService;
    private final MockMvc mvc;

    @Autowired
    AccountConfirmationControllerTest(ConfirmationTokenService confirmationService, UserService userService, MockMvc mvc) {
        this.confirmationService = confirmationService;
        this.userService = userService;
        this.mvc = mvc;
    }

    @Test
    public void confirmAccountGet() throws Exception {
        final ConfirmationToken mockToken = Mockito.mock(ConfirmationToken.class);
        when(mockToken.isTokenValid()).thenReturn(false);
        when(confirmationService.getAccountConfirmationToken("1234456")).thenReturn(Optional.of(mockToken));

        final MvcResult mvcResult = mvc.perform(get("/registration/1234456"))
            .andDo(print())
            .andReturn();
    }
}
