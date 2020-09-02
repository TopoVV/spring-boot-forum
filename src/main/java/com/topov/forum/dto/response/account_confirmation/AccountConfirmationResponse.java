package com.topov.forum.dto.response.account_confirmation;

import com.topov.forum.dto.OperationResult;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AccountConfirmationResponse extends OperationResult {
    protected AccountConfirmationResponse(HttpStatus code, OperationStatus status, String message) {
        super(code, status, message);
    }
}
