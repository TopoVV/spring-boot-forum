package com.topov.forum.dto.response;

import com.topov.forum.dto.OperationResult;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Getter
public class InvalidInput {
    private final Map<String, List<String>> inputErrors;

    public InvalidInput(BindingResult bindingResult) {
        this.inputErrors = bindingResult.getFieldErrors()
            .stream()
            .collect(groupingBy(
                FieldError::getField,
                mapping(FieldError::getDefaultMessage, toList())
            ));
    }
}
