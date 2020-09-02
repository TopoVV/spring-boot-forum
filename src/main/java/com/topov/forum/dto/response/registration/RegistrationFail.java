package com.topov.forum.dto.response.registration;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RegistrationFail extends RegistrationResponse {
    private final Object errors;
    public RegistrationFail(HttpStatus code, String message, Object errors) {
        super(code, OperationStatus.FAIL, message);
        this.errors = errors;
    }
}
