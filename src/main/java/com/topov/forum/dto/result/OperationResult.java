package com.topov.forum.dto.result;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.response.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class OperationResult {
    protected final HttpStatus httpCode;
    protected final String message;

    protected final List<Error> errors = new ArrayList<>();

    protected OperationResult(HttpStatus httpCode, List<? extends Error> errors, String message) {
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

    protected OperationResult(HttpStatus httpCode) {
        this.message = "";
        this.httpCode = httpCode;
    }

    public abstract ResponseEntity<ApiResponse> createResponseEntity();

    protected boolean isSuccessful() {
        return this.errors.isEmpty();
    }

    protected ResponseEntity<ApiResponse> errorResponse() {
        final ErrorResponse error = new ErrorResponse(this.message, "error", this.errors);
        return ResponseEntity.status(this.httpCode).body(error);
    }

    protected ResponseEntity<ApiResponse> successResponse(ApiResponse payload) {
        return ResponseEntity.status(this.httpCode).body(payload);
    }
}
