package com.topov.forum.dto.response.registration;

import com.topov.forum.dto.Errors;
import com.topov.forum.dto.OperationResult;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RegistrationResponse extends OperationResult {
    @Builder
    protected RegistrationResponse(HttpStatus code, String message, Errors errors) {
        super(code, message, errors);
    }
}
