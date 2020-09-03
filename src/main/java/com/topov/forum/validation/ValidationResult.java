package com.topov.forum.validation;

import com.fasterxml.jackson.annotation.JsonIgnore;
<<<<<<< HEAD
import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.error.ValidationError;
=======
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ValidationResult {
<<<<<<< HEAD
    private final List<Error> validationErrors;
    public boolean containsErrors() {
=======
    private final List<ValidationError> validationErrors;
    @JsonIgnore
    public boolean isValid() {
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
        return !validationErrors.isEmpty();
    }
}
