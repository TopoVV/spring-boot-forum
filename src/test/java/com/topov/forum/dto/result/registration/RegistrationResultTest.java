package com.topov.forum.dto.result.registration;

import com.topov.forum.dto.error.ValidationError;
import com.topov.forum.dto.response.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationResultTest {

    @Test
    public void errorResponse() {
        final RegistrationResult registrationResult =
            new RegistrationResult(HttpStatus.BAD_REQUEST, new ValidationError("one", "error"), "bad");

        final ResponseEntity<ApiResponse> responseEntity = registrationResult.createResponseEntity();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void successResponse() {
        final RegistrationResult registrationResult = new RegistrationResult(HttpStatus.OK, "good");
        final ResponseEntity<ApiResponse> responseEntity = registrationResult.createResponseEntity();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
