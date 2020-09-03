package com.topov.forum.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RegistrationResponse extends ApiResponse {
    public RegistrationResponse(String message, String status) {
        super(message, status);
    }
}
