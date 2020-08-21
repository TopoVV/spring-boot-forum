package com.topov.forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Data
@NoArgsConstructor
public class RegistrationResponse {
    private String message;
    private Map<String, List<String>> inputErrors;

    public RegistrationResponse(String message, BindingResult bindingResult) {
        this.message = message;
        this.inputErrors = bindingResult.getFieldErrors()
                                        .stream()
                                        .collect(groupingBy(
                                            FieldError::getField,
                                            mapping(FieldError::getDefaultMessage, toList())
                                        ));
    }
    public RegistrationResponse(String message) {
        this.message = message;
    }
}
