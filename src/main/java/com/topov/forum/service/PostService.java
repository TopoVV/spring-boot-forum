package com.topov.forum.service;

import com.topov.forum.dto.request.CreatePostRequest;
import com.topov.forum.dto.response.CreatePostResponse;

public interface PostService {
    CreatePostResponse createPost(CreatePostRequest createPostRequest);

}
