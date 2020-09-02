package com.topov.forum.validation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ValidationResult {
    private final List<ValidationError> validationErrors;
    @JsonIgnore
    public boolean isValid() {
        return !validationErrors.isEmpty();
    }
}
