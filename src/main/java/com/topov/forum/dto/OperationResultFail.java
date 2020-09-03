package com.topov.forum.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OperationResultFail<T> extends OperationResult {
    private final T errors;
    @Builder
    public OperationResultFail(HttpStatus httpCode, String message, T errors) {
        super(httpCode, message);
        this.errors = errors;
    }
}
