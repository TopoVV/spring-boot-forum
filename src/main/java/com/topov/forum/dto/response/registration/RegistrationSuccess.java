package com.topov.forum.dto.response.registration;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RegistrationSuccess extends RegistrationResponse {
    public RegistrationSuccess(HttpStatus code, String message) {
        super(code, OperationStatus.SUCCESS, message);
    }
}
