package com.topov.forum.dto.response.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.topov.forum.dto.Errors;
import com.topov.forum.dto.OperationResult;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.net.URI;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Getter
public class PostCreateResponse<T> extends OperationResult {
    @JsonInclude(value = NON_NULL)
    private final T data;
    @JsonInclude(value = NON_NULL)
    private final URI location;

    @Builder
    protected PostCreateResponse(HttpStatus httpCode, String message, T data, URI location, Errors errors) {
        super(httpCode, message, errors);
        this.data = data;
        this.location = location;
    }
}
