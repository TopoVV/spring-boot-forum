package com.topov.forum.service;

import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.model.ForumUser;
import com.topov.forum.repository.UserRepository;
import com.topov.forum.service.post.PostService;
import com.topov.forum.service.security.PostServiceSecurity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@MockBeans({ @MockBean(UserRepository.class), @MockBean(PostServiceSecurity.class) })
class PostServiceUT {
    private final UserRepository userRepository;
    private final PostService postService;

    @Autowired
    PostServiceUT(UserRepository repository, PostService postService) {
        userRepository = repository;
        this.postService = postService;
    }

    @Test
    void createPost() {
        final PostCreateRequest postCreateRequestMock = mock(PostCreateRequest.class);
        when(postCreateRequestMock.getTitle()).thenReturn("post title");
        when(postCreateRequestMock.getText()).thenReturn("post text");
        final ForumUser forumUserMock = mock(ForumUser.class);

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(forumUserMock));

        postService.createPost(postCreateRequestMock);
        verify(forumUserMock, only()).addPost(any());
    }
}
