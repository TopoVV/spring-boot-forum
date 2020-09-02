package com.topov.forum.dto;

import com.topov.forum.dto.Errors;
import com.topov.forum.validation.ValidationError;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationErrors extends Errors {
    private final List<ValidationError> validationErrors;

    public ValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
