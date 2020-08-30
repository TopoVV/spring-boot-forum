package com.topov.forum.controller.advice;

import com.topov.forum.controller.RegistrationController;
import com.topov.forum.dto.response.ExceptionalResponse;
import com.topov.forum.exception.RegistrationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice(assignableTypes = { RegistrationController.class })
public class RegistrationControllerAdvice {
    @ExceptionHandler(value = RegistrationException.class)
    public ResponseEntity<ExceptionalResponse> handleRegistrationException(RegistrationException e) {
        log.error("Registration exception", e);
        final ExceptionalResponse response = new ExceptionalResponse("Something went wrong", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
