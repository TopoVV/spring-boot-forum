package com.topov.forum.dto.response.post;

import com.topov.forum.dto.Errors;
import com.topov.forum.dto.OperationResult;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PostEditResponse<T> extends OperationResult {
    private final T data;
    protected PostEditResponse(HttpStatus httpCode, String message, Errors errors, T data) {
        super(httpCode, message, errors);
        this.data = data;
    }
}
