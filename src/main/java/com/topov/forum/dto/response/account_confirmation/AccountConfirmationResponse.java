package com.topov.forum.dto.response.account_confirmation;

import com.topov.forum.dto.Errors;
import com.topov.forum.dto.OperationResult;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AccountConfirmationResponse extends OperationResult {
    @Builder
    protected AccountConfirmationResponse(HttpStatus code, String message, Errors errors) {
        super(code, message, errors);
    }
}
