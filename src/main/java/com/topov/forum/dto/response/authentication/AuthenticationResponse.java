package com.topov.forum.dto.response.authentication;

import com.topov.forum.dto.response.ApiResponse;
import lombok.Getter;

@Getter
public class AuthenticationResponse extends ApiResponse {
    private final String token;
    public AuthenticationResponse(String message, String status, String token) {
        super(message, status);
        this.token = token;
    }
}
