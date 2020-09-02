package com.topov.forum.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OperationResultSuccess<T> extends OperationResult {
    private final T data;
    @Builder
    public OperationResultSuccess(HttpStatus httpCode, String message, T data) {
        super(httpCode, message);
        this.data = data;
    }
}
