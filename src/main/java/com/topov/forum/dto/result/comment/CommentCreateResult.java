package com.topov.forum.dto.result.comment;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.model.CommentDto;
import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.response.comment.CommentCreateResponse;
import com.topov.forum.dto.result.OperationResult;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class CommentCreateResult extends OperationResult {
    private CommentDto commentDto;

    public CommentCreateResult(HttpStatus httpCode, List<? extends Error> errors, String message) {
        super(httpCode, errors, message);
    }

    public CommentCreateResult(HttpStatus httpCode, Error error, String message) {
        super(httpCode, error, message);
    }

    public CommentCreateResult(HttpStatus httpCode, String message, CommentDto commentDto) {
        super(httpCode, message);
        this.commentDto = commentDto;
    }

    @Override
    protected ResponseEntity<ApiResponse> successResponse() {
        CommentCreateResponse payload = new CommentCreateResponse(this.message, "success", this.commentDto);
        return ResponseEntity.status(this.httpCode).body(payload);
    }
}
