package com.topov.forum.dto;

<<<<<<< HEAD
=======
import com.topov.forum.dto.OperationResult;
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
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
