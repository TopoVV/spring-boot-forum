package com.topov.forum.dto.response;

import com.topov.forum.security.jwt.JwtToken;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthenticationResponse extends ApiResponse {
    private final String token;
    public AuthenticationResponse(String message, String status, String token) {
        super(message, status);
        this.token = token;
    }
}
