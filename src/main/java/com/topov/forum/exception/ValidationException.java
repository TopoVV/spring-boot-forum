package com.topov.forum.exception;

<<<<<<< HEAD
import com.topov.forum.dto.error.ValidationError;
=======
import com.topov.forum.validation.ValidationError;
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private final String description;
    private final List<ValidationError> validationErrors;

    public ValidationException(String message, String description, List<ValidationError> validationErrors) {
        super(message);
        this.description = description;
        this.validationErrors = validationErrors;
    }
}
