package com.topov.forum.controller.advice;

import com.topov.forum.controller.CommentController;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice(assignableTypes = { CommentController.class })
public class CommentControllerAdvice {
//    @ExceptionHandler(value = CommentException.class)
//    public ResponseEntity<ExceptionalResponse> handleCommentException(CommentException e) {
//        log.error("Comment exception", e);
//        final ExceptionalResponse response = new ExceptionalResponse("Something went wrong", e.getMessage());
//        return ResponseEntity.badRequest().body(response);
//    }
}
