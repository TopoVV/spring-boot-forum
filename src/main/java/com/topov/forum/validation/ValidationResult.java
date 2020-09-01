package com.topov.forum.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ValidationResult {
    private final String message;
    private final String description;
    private final List<ValidationError> validationErrors;

    public boolean hasErrors() {
        return !validationErrors.isEmpty();
    }
}
