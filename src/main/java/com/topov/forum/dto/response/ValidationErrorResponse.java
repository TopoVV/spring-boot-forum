package com.topov.forum.dto.response;

import com.topov.forum.validation.ValidationError;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class ValidationErrorResponse extends ExceptionalResponse {
    private final List<ValidationError> validationErrors;

    public ValidationErrorResponse(String message, String description, List<ValidationError> validationErrors) {
        super(message, description);
        this.validationErrors = validationErrors;
    }
}
