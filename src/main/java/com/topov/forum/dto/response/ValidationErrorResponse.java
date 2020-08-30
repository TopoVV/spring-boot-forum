package com.topov.forum.dto.response;

import com.topov.forum.dto.response.OperationResponse;
import com.topov.forum.validation.ValidationResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ValidationErrorResponse extends OperationResponse {
    private ValidationResult validationResult;

    public ValidationErrorResponse(ValidationResult validationResult) {
        super("Validation error");
        this.validationResult = validationResult;
    }
}
