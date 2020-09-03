package com.topov.forum.dto.result.registration;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.response.ErrorResponse;
import com.topov.forum.dto.result.OperationResult;
import com.topov.forum.dto.response.RegistrationResponse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class RegistrationResult extends OperationResult {
    public RegistrationResult(HttpStatus httpCode, List<Error> errors, String message) {
        super(httpCode, errors, message);
    }

    public RegistrationResult(HttpStatus httpCode, String message) {
        super(httpCode, message);
    }

    @Override
    public ResponseEntity<ApiResponse> createResponseEntity() {
        if (!this.errors.isEmpty()) {
            final ErrorResponse error = new ErrorResponse(this.message, "error", this.errors);
            return ResponseEntity.status(this.httpCode).body(error);
        } else {
            final RegistrationResponse success = new RegistrationResponse(this.message, "success");
            return ResponseEntity.status(this.httpCode).body(success);
        }
    }
}
