package com.topov.forum.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ValidationResult {
    private final List<ValidationError> validationErrors;
    public boolean isValid() {
        return validationErrors.isEmpty();
    }
}
