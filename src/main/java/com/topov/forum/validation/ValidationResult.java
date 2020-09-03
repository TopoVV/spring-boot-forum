package com.topov.forum.validation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.error.ValidationError;
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
