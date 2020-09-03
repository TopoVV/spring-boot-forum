package com.topov.forum.dto.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.response.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
public abstract class OperationResult {
    protected final HttpStatus httpCode;
    protected final String message;

    protected final List<Error> errors = new ArrayList<>();

    protected OperationResult(HttpStatus httpCode, List<Error> errors, String message) {
        this.httpCode = httpCode;
        this.message = message;
        this.errors.addAll(errors);
    }

    protected OperationResult(HttpStatus httpCode, Error error, String message) {
        this.httpCode = httpCode;
        this.message = message;
        this.errors.add(error);
    }

    protected OperationResult(HttpStatus httpCode, String message) {
        this.httpCode = httpCode;
        this.message = message;
    }

    public abstract ResponseEntity<ApiResponse> createResponseEntity();
}
