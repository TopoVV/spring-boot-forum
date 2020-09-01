package com.topov.forum.exception;

import com.topov.forum.validation.ValidationError;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private final String description;
    private final List<ValidationError> validationErrors;

    public ValidationException(String message, String description, List<ValidationError> validationErrors) {
        super(message);
        this.description = description;
        this.validationErrors = validationErrors;
    }
}
