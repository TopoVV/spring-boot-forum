package com.topov.forum.dto.response.account;

import com.topov.forum.dto.response.ApiResponse;
import lombok.Getter;

@Getter
public class AccountConfirmationResponse extends ApiResponse {
    public AccountConfirmationResponse(String message, String status) {
        super(message, status);
    }
}
