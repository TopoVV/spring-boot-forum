package com.topov.forum.dto.response.authentication;

import com.topov.forum.dto.response.OperationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LoginResponse extends OperationResponse {
    private static final String WELCOME_MESSAGE = "Welcome, %s";
    private static final String BAD_CREDENTIALS_MESSAGE = "Wrong username or password";
    private static final String ACCOUNT_NOT_CONFIRMED_MESSAGE = "Account is not confirmed. " +
        "Please, check your email for appropriate message";
    private static final String AUTHENTICATION_FAILED_MESSAGE = "Something went wrong... Please, try again later";

    public static LoginResponse success(String username) { return new LoginResponse(String.format(WELCOME_MESSAGE, username)); }
    public static LoginResponse badCredentials() { return new LoginResponse(BAD_CREDENTIALS_MESSAGE); }
    public static LoginResponse accountNotEnabled() { return new LoginResponse(ACCOUNT_NOT_CONFIRMED_MESSAGE); }
    public static LoginResponse unknownError() { return new LoginResponse(AUTHENTICATION_FAILED_MESSAGE); }

    private LoginResponse(String message) {
        super(message);
    }
}
