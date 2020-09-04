package com.topov.forum.validation;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.error.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationResult {
    private final List<ValidationError> validationErrors = new ArrayList<>();

    public ValidationResult(List<ValidationError> validationErrors) {
        this.validationErrors.addAll(validationErrors);
    }

    public boolean containsErrors() {
        return !validationErrors.isEmpty();
    }
}
