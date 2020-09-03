package com.topov.forum.dto.error;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ValidationError extends Error {
    private final String invalidProperty;
    public ValidationError(String invalidProperty, String description) {
        super(description);
        this.invalidProperty = invalidProperty;
    }
}
