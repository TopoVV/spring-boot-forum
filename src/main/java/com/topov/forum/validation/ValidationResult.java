package com.topov.forum.validation;

import com.topov.forum.dto.error.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ValidationResult {
    private final List<Error> validationErrors;
    public boolean containsErrors() {
        return !validationErrors.isEmpty();
    }
}
