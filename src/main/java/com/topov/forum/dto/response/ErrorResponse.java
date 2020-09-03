package com.topov.forum.dto.response;

import com.topov.forum.dto.error.Error;
import lombok.Getter;

import java.util.List;

@Getter
public class ErrorResponse extends ApiResponse {
    private final List<Error> errors;
    public ErrorResponse(String message, String status, List<Error> errors) {
        super(message, status);
        this.errors = errors;
    }
}
