package com.topov.forum.dto.result.comment;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.model.CommentDto;
import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.response.comment.CommentGetAllResponse;
import com.topov.forum.dto.result.OperationResult;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class CommentGetAllResult extends OperationResult {
    private Page<CommentDto> comments = Page.empty();

    public CommentGetAllResult(HttpStatus httpCode, Page<CommentDto> comments) {
        super(httpCode);
        this.comments = comments;
    }

    public CommentGetAllResult(HttpStatus httpCode, Error error, String message) {
        super(httpCode, error, message);
    }

    public CommentGetAllResult(HttpStatus httpCode, List<? extends Error> errors, String message) {
        super(httpCode, errors, message);
    }

    @Override
    public ResponseEntity<ApiResponse> createResponseEntity() {
        if (super.isSuccessful()) {
            final CommentGetAllResponse success = new CommentGetAllResponse("success", this.comments);
            return ResponseEntity.status(this.httpCode).body(success);
        }
        return super.errorResponse();
    }
}
