package com.topov.forum.dto.result.post;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.model.PostDto;
import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.response.ErrorResponse;
import com.topov.forum.dto.response.PostCreateResponse;
import com.topov.forum.dto.response.RegistrationResponse;
import com.topov.forum.dto.result.OperationResult;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

@Getter
public class PostCreateResult extends OperationResult {
    private URI location;
    private PostDto postDto;

    public PostCreateResult(HttpStatus httpCode, List<Error> errors, String message) {
        super(httpCode, errors, message);
    }

    public PostCreateResult(HttpStatus httpCode, String message, URI location, PostDto postDto) {
        super(httpCode, message);
        this.location = location;
        this.postDto = postDto;
    }

    @Override
    public ResponseEntity<ApiResponse> createResponseEntity() {
        if (!this.errors.isEmpty()) {
            final ErrorResponse error = new ErrorResponse(this.message, "error", this.errors);
            return ResponseEntity.status(this.httpCode).body(error);
        } else {
            final PostCreateResponse success = new PostCreateResponse(this.message, "success", postDto);
            return ResponseEntity.status(this.httpCode).location(this.location).body(success);
        }
    }
}
