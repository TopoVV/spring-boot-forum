package com.topov.forum.dto.response.registration;

import com.topov.forum.dto.response.OperationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RegistrationResponse extends OperationResponse {
    private static final String REGULAR_USER_REGISTERED_SUCCESSFULLY = "You've been successfully registered! " +
        "Please, confirm your account by clicking the link that was sent to %s";
    private static final String INVALID_SUPERUSER_TOKEN = "Invalid token";
    private static final String SUPERUSER_REGISTERED_SUCCESSFULLY = "The superuser has been successfully registered";

    public static RegistrationResponse superuserRegistrationSuccess() {
        return new RegistrationResponse(SUPERUSER_REGISTERED_SUCCESSFULLY);
    }

    public static RegistrationResponse invalidToken() {
        return new RegistrationResponse(INVALID_SUPERUSER_TOKEN);
    }

    public static RegistrationResponse regularUserRegistrationSuccess(String recipient) {
        return new RegistrationResponse(String.format(REGULAR_USER_REGISTERED_SUCCESSFULLY, recipient));
    }

    private RegistrationResponse(String message) {
        super(message);
    }

}
