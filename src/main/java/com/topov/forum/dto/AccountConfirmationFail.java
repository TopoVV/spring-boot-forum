package com.topov.forum.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AccountConfirmationFail extends AccountConfirmationResponse {
    private final Object errors;

    public AccountConfirmationFail(HttpStatus code, String message, Object errors) {
        super(code, OperationStatus.FAIL, message);
        this.errors = errors;
    }
}
