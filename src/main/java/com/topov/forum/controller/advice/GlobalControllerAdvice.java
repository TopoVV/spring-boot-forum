package com.topov.forum.controller.advice;

import com.topov.forum.dto.response.ExceptionalResponse;
import com.topov.forum.dto.response.InvalidInputResponse;
import com.topov.forum.dto.response.ValidationErrorResponse;
import com.topov.forum.exception.ValidationException;
import com.topov.forum.validation.ValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = ResponseStatusException.class)
    public ResponseEntity<ExceptionalResponse> handleResponseStatusException(ResponseStatusException ex) {
        final HttpStatus status = ex.getStatus();
        final String message = ex.getMessage();
        final ExceptionalResponse exceptionalResponse = new ExceptionalResponse("Error", message);
        return ResponseEntity.status(status).body(exceptionalResponse);
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(ValidationException ex) {
        final String description = ex.getDescription();
        final String message = ex.getMessage();
        final List<ValidationError> validationErrors = ex.getValidationErrors();
        final var response = new ValidationErrorResponse(message, description, validationErrors);
        return ResponseEntity.badRequest().body(response);
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
