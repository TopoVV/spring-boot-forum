package com.topov.forum.service;

import com.topov.forum.dto.request.CreatePostRequest;
import com.topov.forum.dto.request.EditPostRequest;
import com.topov.forum.dto.response.CreatePostResponse;
import com.topov.forum.dto.response.EditPostResponse;

public interface PostService {
    CreatePostResponse createPost(CreatePostRequest createPostRequest);
    EditPostResponse editPost(EditPostRequest editPostRequest);
    void deletePost(Long postId);

}
