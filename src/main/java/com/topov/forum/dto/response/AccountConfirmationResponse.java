package com.topov.forum.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountConfirmationResponse extends ApiResponse {
    public AccountConfirmationResponse(String message, String status) {
        super(message, status);
    }
}
