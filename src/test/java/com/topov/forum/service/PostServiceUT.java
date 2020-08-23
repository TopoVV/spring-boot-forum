package com.topov.forum.service;

import com.topov.forum.WithMockForumUserDetails;
import com.topov.forum.dto.request.CreatePostRequest;
import com.topov.forum.dto.response.CreatePostResponse;
import com.topov.forum.model.ForumUser;
import com.topov.forum.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@MockBeans({ @MockBean(UserRepository.class) })
class PostServiceUT {
    private final UserRepository userRepository;
    private final PostService postService;

    @Autowired
    PostServiceUT(UserRepository repository, PostService postService) {
        userRepository = repository;
        this.postService = postService;
    }

    @Test
    @WithMockForumUserDetails(username = "user")
    void createPost() {
        final CreatePostRequest createPostRequestMock = mock(CreatePostRequest.class);
        when(createPostRequestMock.getTitle()).thenReturn("post title");
        when(createPostRequestMock.getText()).thenReturn("post text");
        final ForumUser forumUserMock = mock(ForumUser.class);

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(forumUserMock));

        final CreatePostResponse response = postService.createPost(createPostRequestMock);
        verify(forumUserMock, only()).addPost(any());
    }
}
