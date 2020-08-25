package com.topov.forum.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ValidationError extends OperationResponse {
    private Map<String, List<String>> inputErrors;

    public ValidationError(BindingResult bindingResult) {
        super("Invalid input");
        this.inputErrors = bindingResult.getFieldErrors()
            .stream()
            .collect(groupingBy(
                FieldError::getField,
                mapping(FieldError::getDefaultMessage, toList())
            ));
    }

}
