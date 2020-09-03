package com.topov.forum.dto.response;

import com.topov.forum.dto.model.PostDto;
import lombok.Getter;

@Getter
public class PostEditResponse extends ApiResponse {
    private final PostDto postDto;
    public PostEditResponse(String message, String status, PostDto postDto) {
        super(message, status);
        this.postDto = postDto;
    }
}
