package com.topov.forum.dto;

import com.topov.forum.validation.ValidationError;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationErrors {
    private final List<ValidationError> validationErrors;
    public ValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
