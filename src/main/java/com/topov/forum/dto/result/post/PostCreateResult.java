package com.topov.forum.dto.result.post;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.model.post.PostDto;
import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.response.post.PostCreateResponse;
import com.topov.forum.dto.result.OperationResult;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

@Getter
public class PostCreateResult extends OperationResult {
    private URI resourceLocation;
    private PostDto postDto;

    public PostCreateResult(HttpStatus httpCode, List<? extends Error> errors, String message) {
        super(httpCode, errors, message);
    }

    public PostCreateResult(HttpStatus httpCode, String message, URI resourceLocation, PostDto postDto) {
        super(httpCode, message);
        this.resourceLocation = resourceLocation;
        this.postDto = postDto;
    }

    @Override
    protected ResponseEntity<ApiResponse> successResponse() {
        final PostCreateResponse payload = new PostCreateResponse(this.message, "success", postDto);
        return ResponseEntity.status(this.httpCode).body(payload);
    }
}
