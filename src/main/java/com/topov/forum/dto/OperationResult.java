package com.topov.forum.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
public abstract class OperationResult {
    private final Integer httpCode;
    private final String message;

    protected OperationResult(HttpStatus httpCode, String message) {
        this.httpCode = httpCode.value();
        this.message = message;
    }
}
