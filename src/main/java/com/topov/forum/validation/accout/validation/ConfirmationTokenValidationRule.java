package com.topov.forum.validation.accout.validation;

import com.topov.forum.validation.ValidationRule;
import com.topov.forum.validation.accout.constraint.AccountConfirmationTokenValid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@AccountConfirmationTokenValid
public class ConfirmationTokenValidationRule extends ValidationRule {
    private final String tokenValue;

    public ConfirmationTokenValidationRule(String tokenValue) {
        this.tokenValue = tokenValue;
    }
}
