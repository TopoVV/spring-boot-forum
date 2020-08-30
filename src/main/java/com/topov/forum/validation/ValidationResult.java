package com.topov.forum.validation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationResult {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationError> validationErrors;

    public ValidationResult(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public boolean hasErrors() {
        return validationErrors.size() > 0;
    }
}
