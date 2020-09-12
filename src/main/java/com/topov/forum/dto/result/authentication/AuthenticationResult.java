package com.topov.forum.dto.result.authentication;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.response.authentication.AuthenticationResponse;
import com.topov.forum.dto.result.OperationResult;
import com.topov.forum.security.jwt.JwtToken;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class AuthenticationResult extends OperationResult {
    private JwtToken token;
    private String authorizationHeader;
    private String jwtPrefix;

    public AuthenticationResult(HttpStatus httpCode, List<? extends Error> errors, String message) {
        super(httpCode, errors, message);
    }

    public AuthenticationResult(HttpStatus httpCode, Error error, String message) {
        super(httpCode, error, message);

    }

    public AuthenticationResult(HttpStatus httpCode, String message, JwtToken token, String authorizationHeader, String jwtPrefix) {
        super(httpCode, message);
        this.token = token;
        this.authorizationHeader = authorizationHeader;
        this.jwtPrefix = jwtPrefix;
    }

    @Override
    protected ResponseEntity<ApiResponse> successResponse() {
        final String tokenValue = this.token.getTokenValue();
        final AuthenticationResponse payload = new AuthenticationResponse(this.message, "success", tokenValue);
        return ResponseEntity.status(this.httpCode).body(payload);
    }

}
