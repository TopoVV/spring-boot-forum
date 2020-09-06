package com.topov.forum.dto.result.post;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.model.post.PostDto;
import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.response.post.PostGetAllResponse;
import com.topov.forum.dto.result.OperationResult;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class PostGetAllResult extends OperationResult {
    private Page<PostDto> posts = Page.empty();

    public PostGetAllResult(HttpStatus httpCode, List<? extends Error> errors, String message) {
        super(httpCode, errors, message);
    }

    public PostGetAllResult(HttpStatus httpCode, Error error, String message) {
        super(httpCode, error, message);
    }

    public PostGetAllResult(HttpStatus httpCode, Page<PostDto> posts) {
        super(httpCode);
        this.posts = posts;
    }

    @Override
    public ResponseEntity<ApiResponse> createResponseEntity() {
        final PostGetAllResponse success = new PostGetAllResponse("success", this.posts);
        return ResponseEntity.status(this.httpCode).body(success);
    }
}
