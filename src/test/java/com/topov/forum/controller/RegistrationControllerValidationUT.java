package com.topov.forum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topov.forum.dto.request.RegistrationRequest;
import com.topov.forum.dto.request.SuperuserRegistrationRequest;
import com.topov.forum.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerValidationUT {
    private final ObjectMapper mapper;
    private final MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    public RegistrationControllerValidationUT(ObjectMapper mapper, MockMvc mvc) {
        this.mapper = mapper;
        this.mvc = mvc;
    }

    @Test
    void whenUsernameNotUnique_ThenResponseMustContainEmailBindingError() throws Exception {
        final RegistrationRequest request = new RegistrationRequest("username", "password", "email@email.com");
        when(userRepository.existsByUsername("username")).thenReturn(true);
        final String jsonRequest = mapper.writeValueAsString(request);

        mvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON)
                                         .content(jsonRequest))
           .andDo(print())
           .andExpect(jsonPath("$.inputErrors.username[0]", is("The specified username is already in use")));

    }

    @Test
    void whenEmailNotUnique_ThenResponseMustContainUsernameBindingError() throws Exception {
        final RegistrationRequest request = new RegistrationRequest("username", "password", "email@email.com");
        when(userRepository.existsByEmail("email@email.com")).thenReturn(true);
        final String jsonRequest = mapper.writeValueAsString(request);

        mvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON)
                                         .content(jsonRequest))
           .andDo(print())
           .andExpect(jsonPath("$.inputErrors.email[0]", is("The specified email is already in use")));

    }

    @Test
    void whenInvalidToken_ThenNoOtherFieldsValidated() throws Exception {
        final SuperuserRegistrationRequest request = new SuperuserRegistrationRequest("", "", "password", "email@email.com");
        final String jsonRequest = mapper.writeValueAsString(request);

        when(userRepository.existsByEmail("email@email.com")).thenReturn(true);

        mvc.perform(post("/registration/superuser").contentType(MediaType.APPLICATION_JSON)
                                                   .content(jsonRequest))
           .andDo(print())
           .andExpect(jsonPath("$.inputErrors.token", hasSize(1)));

    }
}
