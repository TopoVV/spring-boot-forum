package com.topov.forum.dto.response.post;

import com.topov.forum.dto.model.post.PostDto;
import com.topov.forum.dto.response.ApiResponse;
import lombok.Getter;

@Getter
public class PostCreateResponse extends ApiResponse {
    private final PostDto postDto;
    public PostCreateResponse(String message, String status, PostDto postDto) {
        super(message, status);
        this.postDto = postDto;
    }
}
