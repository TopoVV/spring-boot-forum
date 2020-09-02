package com.topov.forum.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class OperationResult {
    private final Integer code;
    private final OperationStatus status;
    private final String message;

    protected OperationResult(HttpStatus code, OperationStatus status, String message) {
        this.code = code.value();
        this.status = status;
        this.message = message;
    }
}
