package com.topov.forum.dto.result.post;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.model.post.PostDto;
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
    protected ResponseEntity<ApiResponse> successResponse() {
        final PostEditResponse payload = new PostEditResponse(this.message, "success", postDto);
        return ResponseEntity.status(this.httpCode).body(payload);
    }
}
