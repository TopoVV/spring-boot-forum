package com.topov.forum.validation;

import com.topov.forum.dto.error.Error;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationResult {
    private final List<Error> validationErrors = new ArrayList<>();

    public ValidationResult(List<Error> validationErrors) {
        this.validationErrors.addAll(validationErrors);
    }

    public boolean containsErrors() {
        return !validationErrors.isEmpty();
    }
}
