package com.topov.forum.dto.result.post;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.response.post.PostDeleteResponse;
import com.topov.forum.dto.result.OperationResult;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class PostDeleteResult extends OperationResult {

    public PostDeleteResult(HttpStatus httpCode, List<? extends Error> errors, String message) {
        super(httpCode, errors, message);
    }

    public PostDeleteResult(HttpStatus httpCode, Error error, String message) {
        super(httpCode, error, message);
    }

    public PostDeleteResult(HttpStatus httpCode, String message) {
        super(httpCode, message);
    }

    @Override
    public ResponseEntity<ApiResponse> createResponseEntity() {
        if (super.isSuccessful()) {
            final PostDeleteResponse success = new PostDeleteResponse(this.message, "success");
            return super.successResponse(success);
        }
        return super.errorResponse();
    }
}
