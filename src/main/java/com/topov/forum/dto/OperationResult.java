package com.topov.forum.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class OperationResult {
    private final Integer httpCode;
    private final String message;

    protected OperationResult(HttpStatus httpCode, String message) {
        this.httpCode = httpCode.value();
        this.message = message;
    }
}
