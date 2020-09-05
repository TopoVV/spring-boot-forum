package com.topov.forum.dto.response.comment;

import com.topov.forum.dto.model.CommentDto;
import com.topov.forum.dto.response.ApiResponse;
import lombok.Getter;

@Getter
public class CommentCreateResponse extends ApiResponse {
    private final CommentDto commentDto;
    public CommentCreateResponse(String message, String status, CommentDto commentDto) {
        super(message, status);
        this.commentDto = commentDto;
    }
}
