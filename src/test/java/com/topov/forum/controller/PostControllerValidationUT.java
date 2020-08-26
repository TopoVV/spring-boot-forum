package com.topov.forum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasKey;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@MockBeans({ @MockBean(PostRepository.class) })
@AutoConfigureMockMvc
class PostControllerValidationUT {
    private final ObjectMapper mapper;
    private final PostRepository postRepository;
    private final MockMvc mockMvc;

    @Autowired
    PostControllerValidationUT(ObjectMapper mapper, PostRepository postRepository, MockMvc mockMvc) {
        this.mapper = mapper;
        this.postRepository = postRepository;
        this.mockMvc = mockMvc;
    }

    @Test
    void whenTitleNotUnique_ThenValidationError() throws Exception {
        final PostCreateRequest postCreateRequest = new PostCreateRequest("post text", "post title");

        when(postRepository.existsByTitle("post title")).thenReturn(true);
        final String json = mapper.writeValueAsString(postCreateRequest);
        final MvcResult mvcResult = mockMvc.perform(post("/posts")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(json))
            .andDo(print())
            .andExpect(jsonPath("inputErrors", hasKey("title")))
            .andReturn();

        System.out.println(mvcResult);


    }


    @Test
    @WithMockUser(roles = "SUPERUSER")
    void test() throws Exception {
        mockMvc.perform(get("/posts"))
            .andDo(print())
            .andReturn();
    }

}
