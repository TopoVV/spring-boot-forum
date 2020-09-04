package com.topov.forum.dto.result.post;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.model.PostDto;
import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.response.post.PostEditResponse;
import com.topov.forum.dto.result.OperationResult;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class PostEditResult extends OperationResult {
    private PostDto postDto;

    public PostEditResult(HttpStatus httpCode, List<? extends Error> errors, String message) {
        super(httpCode, errors, message);
    }

    public PostEditResult(HttpStatus httpCode, String message, PostDto postDto) {
        super(httpCode, message);
        this.postDto = postDto;
    }

    @Override
    public ResponseEntity<ApiResponse> createResponseEntity() {
        if (super.isSuccessful()) {
            final PostEditResponse success = new PostEditResponse(this.message, "success", postDto);
            return super.successResponse(success);
        }
        return super.errorResponse();
    }
}
