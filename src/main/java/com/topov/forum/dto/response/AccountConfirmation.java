package com.topov.forum.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountConfirmation extends OperationResponse {
    private static final String ACCOUNT_CONFIRMED = "Account confirmed! Thank you";
    private static final String ACCOUNT_NOT_CONFIRMED = "Account is not confirmed. Reason: %s";

    public static AccountConfirmation success() {
        return new AccountConfirmation(true, ACCOUNT_CONFIRMED);
    }

    public static AccountConfirmation failed(String cause) {
        return new AccountConfirmation(false, String.format(ACCOUNT_NOT_CONFIRMED, cause));
    }

    @JsonIgnore
    private Boolean isConfirmed;
    private String description;
}
