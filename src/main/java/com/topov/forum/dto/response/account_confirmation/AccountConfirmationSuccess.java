package com.topov.forum.dto.response.account_confirmation;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AccountConfirmationSuccess extends AccountConfirmationResponse {
    public AccountConfirmationSuccess(HttpStatus code, String message) {
        super(code, OperationStatus.SUCCESS, message);
    }
}
