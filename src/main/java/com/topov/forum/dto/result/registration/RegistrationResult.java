package com.topov.forum.dto.result.registration;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.response.registration.RegistrationResponse;
import com.topov.forum.dto.result.OperationResult;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class RegistrationResult extends OperationResult {
    public RegistrationResult(HttpStatus httpCode, List<? extends Error> errors, String message) {
        super(httpCode, errors, message);
    }

    public RegistrationResult(HttpStatus httpCode, String message) {
        super(httpCode, message);
    }

    @Override
    public ResponseEntity<ApiResponse> createResponseEntity() {
        if (super.isSuccessful()) {
            final RegistrationResponse success = new RegistrationResponse(this.message, "success");
            return super.successResponse(success);
        }
        return super.errorResponse();
    }
}
