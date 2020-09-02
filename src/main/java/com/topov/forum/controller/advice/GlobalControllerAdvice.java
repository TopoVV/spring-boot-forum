package com.topov.forum.controller.advice;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(value = ResponseStatusException.class)
//    public ResponseEntity<ExceptionalResponse> handleResponseStatusException(ResponseStatusException ex) {
//        final HttpStatus status = ex.getStatus();
//        final String message = ex.getMessage();
//        final ExceptionalResponse exceptionalResponse = new ExceptionalResponse("Error", message);
//        return ResponseEntity.status(status).body(exceptionalResponse);
//    }
//
//    @ExceptionHandler(value = ValidationException.class)
//    public ResponseEntity<ValidationErrorResponse> handleValidationException(ValidationException ex) {
//        final String description = ex.getDescription();
//        final String message = ex.getMessage();
//        final List<ValidationError> validationErrors = ex.getValidationErrors();
//        final var response = new ValidationErrorResponse(message, description, validationErrors);
//        return ResponseEntity.badRequest().body(response);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers,
//                                                                  HttpStatus status,
//                                                                  WebRequest request) {
//        final BindingResult bindingResult = ex.getBindingResult();
//        final InvalidInput invalidInput = new InvalidInput(bindingResult);
//        return ResponseEntity.badRequest().body(invalidInput);
//    }
}
