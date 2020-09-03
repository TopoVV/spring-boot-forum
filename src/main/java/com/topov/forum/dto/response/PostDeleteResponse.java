package com.topov.forum.dto.response;

import lombok.Getter;

@Getter
public class PostDeleteResponse extends ApiResponse {
    public PostDeleteResponse(String message, String status) {
        super(message, status);
    }
}
