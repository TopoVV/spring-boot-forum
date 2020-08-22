package com.topov.forum.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topov.forum.dto.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoginServiceUT {
    private final MockMvc mvc;
    private final ObjectMapper mapper;

    @Autowired
    LoginServiceUT(MockMvc mvc, ObjectMapper mapper) {
        this.mvc = mvc;
        this.mapper = mapper;
    }


    @Test
    public void testAuthenctication() throws Exception {
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("passwwqwqord");
        final String json = mapper.writeValueAsString(loginRequest);
        mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON_VALUE)
                                  .content(json))
           .andDo(print())
           .andReturn();
    }
}
