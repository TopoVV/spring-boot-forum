package com.topov.forum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
@MockBeans({ @MockBean(UserRepository.class) })
@ActiveProfiles("test")
class RegistrationControllerValidationUT {
    private final ObjectMapper mapper;
    private final MockMvc mvc;
    private final UserRepository userRepository;

    @Autowired
    RegistrationControllerValidationUT(ObjectMapper mapper, MockMvc mvc, UserRepository repository) {
        this.mapper = mapper;
        this.mvc = mvc;
        userRepository = repository;
    }

    @Test
    void whenUsernameNotUnique_ThenResponseMustContainUsernameBindingError() throws Exception {
        final RegistrationRequest request = new RegistrationRequest("username", "password", "email@email.com");
        when(userRepository.existsByUsername("username")).thenReturn(true);
        final String jsonRequest = mapper.writeValueAsString(request);

        mvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON)
                                         .content(jsonRequest))
           .andDo(print())
           .andExpect(jsonPath("$.errors[0].invalidProperty", is("username")))
           .andExpect(jsonPath("$.errors[0].error", is("The specified username is already in use")));

    }

    @Test
    void whenEmailNotUnique_ThenResponseMustContainEmailBindingError() throws Exception {
        final RegistrationRequest request = new RegistrationRequest("username", "password", "email@email.com");
        when(userRepository.existsByEmail("email@email.com")).thenReturn(true);
        final String jsonRequest = mapper.writeValueAsString(request);

        mvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON)
                                         .content(jsonRequest))
            .andDo(print())
            .andExpect(jsonPath("$.errors[0].invalidProperty", is("email")))
            .andExpect(jsonPath("$.errors[0].error", is("The specified email is already in use")));

    }
}
