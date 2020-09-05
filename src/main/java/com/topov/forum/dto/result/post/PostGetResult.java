package com.topov.forum.dto.result.post;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.model.post.PostDto;
import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.response.ErrorResponse;
import com.topov.forum.dto.response.post.PostGetResponse;
import com.topov.forum.dto.result.OperationResult;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class PostGetResult extends OperationResult {
    private PostDto postDto;

    protected PostGetResult(HttpStatus httpCode, List<? extends Error> errors, String message) {
        super(httpCode, errors, message);
    }

    protected PostGetResult(HttpStatus httpCode, Error error, String message) {
        super(httpCode, error, message);
    }

    public PostGetResult(HttpStatus httpCode, PostDto postDto) {
        super(httpCode);
        this.postDto = postDto;
    }

    @Override
    public ResponseEntity<ApiResponse> createResponseEntity() {
        if (super.isSuccessful()) {
            final PostGetResponse success = new PostGetResponse("success", this.postDto);
            return ResponseEntity.status(this.httpCode).body(success);
        }
        return ResponseEntity.badRequest().body(null);

    }
}