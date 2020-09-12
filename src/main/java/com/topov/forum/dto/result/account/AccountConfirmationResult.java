package com.topov.forum.dto.result.account;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.response.account.AccountConfirmationResponse;
import com.topov.forum.dto.result.OperationResult;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class AccountConfirmationResult extends OperationResult {
    public AccountConfirmationResult(HttpStatus httpCode, List<? extends Error> errors, String message) {
        super(httpCode, errors, message);
    }

    public AccountConfirmationResult(HttpStatus httpCode, Error error, String message) {
        super(httpCode, error, message);
    }

    public AccountConfirmationResult(HttpStatus httpCode, String message) {
        super(httpCode, message);
    }

    @Override
    protected ResponseEntity<ApiResponse> successResponse() {
        final AccountConfirmationResponse payload = new AccountConfirmationResponse(this.message, "success");
        return ResponseEntity.status(this.httpCode).body(payload);
    }
}
