package com.topov.forum.controller;

import com.topov.forum.repository.AccountConfirmationTokenRepository;
import com.topov.forum.service.user.UserService;
import com.topov.forum.token.AccountConfirmationToken;
import com.topov.forum.validation.accout.rule.ConfirmationTokenValidationRule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@MockBeans({
    @MockBean(AccountConfirmationTokenRepository.class),
    @MockBean(UserService.class)
})
class AccountConfirmationControllerTest {
    private final AccountConfirmationTokenRepository confirmationTokenRepository;
    private final MockMvc mvc;

    @Autowired
    AccountConfirmationControllerTest(AccountConfirmationTokenRepository confirmationTokenRepository, MockMvc mvc) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.mvc = mvc;
    }

    @Test
    public void confirmAccountGet() throws Exception {
        final AccountConfirmationToken mockToken = Mockito.mock(AccountConfirmationToken.class);
        when(mockToken.isTokenValid()).thenReturn(false);
        when(confirmationTokenRepository.findTokenByTokenValue("1234456")).thenReturn(Optional.of(mockToken));

        final MvcResult mvcResult = mvc.perform(get("/registration/1234456"))
            .andDo(print())
            .andReturn();
    }
}
