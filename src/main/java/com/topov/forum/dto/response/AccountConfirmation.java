package com.topov.forum.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@JsonIgnoreProperties(value = {"isConfirmed"})
public class AccountConfirmation {
    public static AccountConfirmation success() {
        return new AccountConfirmation(true, "Account confirmed! Thank you");
    }

    public static AccountConfirmation failed(String cause) {
        return new AccountConfirmation(false, String.format("Account is not confirmed. %s", cause));
    }

    private final boolean isConfirmed;
    private final String description;
}
