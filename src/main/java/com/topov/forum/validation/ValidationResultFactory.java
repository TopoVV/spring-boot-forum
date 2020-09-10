package com.topov.forum.validation;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.error.ValidationError;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@SuppressWarnings("unchecked cast")
public class ValidationResultFactory {
    public <T> ValidationResult createValidationResult(Set<ConstraintViolation<T>> violations) {
        List<Error> validationErrors = new ArrayList<>();
        for (final var violation : violations) {
            final String description = violation.getMessage();
            final String invalid = violation.getPropertyPath().toString();
            validationErrors.add(new ValidationError(invalid, description));
        }
        return new ValidationResult(validationErrors);
    }
}
