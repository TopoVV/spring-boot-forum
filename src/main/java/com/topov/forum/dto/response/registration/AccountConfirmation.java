package com.topov.forum.dto.response.registration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.topov.forum.dto.response.OperationResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccountConfirmation extends OperationResponse {
    private static final String ACCOUNT_CONFIRMED = "Account confirmed! Thank you";
    private static final String ACCOUNT_NOT_CONFIRMED = "Account is not confirmed. Reason: %s";
    private static final String INVALID_CONFIRMATION_TOKEN = "Invalid token";

    public static AccountConfirmation success() {
        return new AccountConfirmation(ACCOUNT_CONFIRMED);
    }

    public static AccountConfirmation invalidToken() {
        return new AccountConfirmation(String.format(ACCOUNT_NOT_CONFIRMED, INVALID_CONFIRMATION_TOKEN));
    }

    private AccountConfirmation(String message) {
        super(message);
    }
}
