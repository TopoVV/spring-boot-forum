package com.topov.forum.controller.advice;

import com.topov.forum.dto.response.RegistrationResponse;
import com.topov.forum.exception.RegistrationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class RegistrationControllerAdvice {
    @ExceptionHandler(value = RegistrationException.class)
    public ResponseEntity<RegistrationResponse> handleRegistrationException(RegistrationException e) {
        log.error("Registration exception", e);
        final RegistrationResponse response = new RegistrationResponse(e.getCause().getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
