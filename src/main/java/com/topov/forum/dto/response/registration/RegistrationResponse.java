package com.topov.forum.dto.response.registration;

import com.topov.forum.dto.OperationResult;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class RegistrationResponse extends OperationResult {
    protected RegistrationResponse(HttpStatus code, OperationStatus status, String message) {
        super(code, status, message);
    }
}
