package com.topov.forum.dto.result.comment;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.response.comment.CommentDeleteResponse;
import com.topov.forum.dto.response.comment.CommentEditResponse;
import com.topov.forum.dto.result.OperationResult;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class CommentDeleteResult extends OperationResult {
    public CommentDeleteResult(HttpStatus httpCode, List<? extends Error> errors, String message) {
        super(httpCode, errors, message);
    }

    public CommentDeleteResult(HttpStatus httpCode, Error error, String message) {
        super(httpCode, error, message);
    }

    public CommentDeleteResult(HttpStatus httpCode, String message) {
        super(httpCode, message);
    }

    @Override
    protected ResponseEntity<ApiResponse> successResponse() {
        final CommentDeleteResponse payload = new CommentDeleteResponse(this.message, "success");
        return ResponseEntity.status(this.httpCode).body(payload);
    }
}
