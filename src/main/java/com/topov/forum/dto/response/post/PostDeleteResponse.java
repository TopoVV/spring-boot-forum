package com.topov.forum.dto.response.post;

import com.topov.forum.dto.response.ApiResponse;
import lombok.Getter;

@Getter
public class PostDeleteResponse extends ApiResponse {
    public PostDeleteResponse(String message, String status) {
        super(message, status);
    }
}
