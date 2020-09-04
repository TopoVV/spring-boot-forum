package com.topov.forum.dto.response.comment;

import com.topov.forum.dto.model.CommentDto;
import com.topov.forum.dto.response.ApiResponse;
import lombok.Getter;

@Getter
public class CommentEditResponse extends ApiResponse {
    private final CommentDto commendDto;
    public CommentEditResponse(String message, String status, CommentDto commendDto) {
        super(message, status);
        this.commendDto = commendDto;
    }
}
