package com.topov.forum.dto.response.comment;

import com.topov.forum.dto.response.ApiResponse;
import lombok.Getter;

@Getter
public class CommentDeleteResponse extends ApiResponse {
    public CommentDeleteResponse(String message, String status) {
        super(message, status);
    }
}
