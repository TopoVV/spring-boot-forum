package com.topov.forum.controller.advice;

import com.topov.forum.controller.CommentController;
import com.topov.forum.dto.response.ExceptionalResponse;
import com.topov.forum.exception.CommentException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice(assignableTypes = { CommentController.class })
public class CommentControllerAdvice {
    @ExceptionHandler(value = CommentException.class)
    public ResponseEntity<ExceptionalResponse> handleCommentException(CommentException e) {
        log.error("Comment exception", e);
        final ExceptionalResponse response = new ExceptionalResponse("Something went wrong", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
