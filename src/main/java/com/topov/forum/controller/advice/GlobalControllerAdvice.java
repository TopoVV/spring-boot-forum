package com.topov.forum.controller.advice;

import com.topov.forum.dto.response.ExceptionalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(value = ResponseStatusException.class)
    public ResponseEntity<ExceptionalResponse> handleResponseStatusException(ResponseStatusException e) {
        final HttpStatus status = e.getStatus();
        final String message = e.getMessage();
        final ExceptionalResponse exceptionalResponse = new ExceptionalResponse("Error", message);
        return ResponseEntity.status(status).body(exceptionalResponse);
    }
}
