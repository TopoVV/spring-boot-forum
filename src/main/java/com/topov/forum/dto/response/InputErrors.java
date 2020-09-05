package com.topov.forum.dto.response;

import com.topov.forum.dto.error.ValidationError;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
public class InputErrors extends ApiResponse {
    private final List<ValidationError> errors;
    public InputErrors(BindingResult bindingResult) {
        super("Invalid input", "error");
        this.errors = bindingResult.getFieldErrors()
            .stream()
            .map(er -> new ValidationError(er.getField(), er.getDefaultMessage()))
            .collect(toList());
    }
}
