package com.topov.forum.controller.advice;

import com.topov.forum.dto.response.ExceptionalResponse;
import com.topov.forum.dto.response.InvalidInputResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = ResponseStatusException.class)
    public ResponseEntity<ExceptionalResponse> handleResponseStatusException(ResponseStatusException e) {
        final HttpStatus status = e.getStatus();
        final String message = e.getMessage();
        final ExceptionalResponse exceptionalResponse = new ExceptionalResponse("Error", message);
        return ResponseEntity.status(status).body(exceptionalResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        final BindingResult bindingResult = ex.getBindingResult();
        final InvalidInputResponse invalidInputResponse = new InvalidInputResponse(bindingResult);
        return ResponseEntity.badRequest().body(invalidInputResponse);
    }
}
