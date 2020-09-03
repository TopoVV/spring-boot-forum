package com.topov.forum.controller.advice;

import com.topov.forum.controller.PostController;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice(assignableTypes = { PostController.class })
public class PostControllerAdvice {
//    @ExceptionHandler(value = PostException.class)
//    public ResponseEntity<ExceptionalResponse> handlePostException(PostException e) {
//        log.error("Post exception", e);
//        final ExceptionalResponse response = new ExceptionalResponse("Something went wrong", e.getMessage());
//        return ResponseEntity.badRequest().body(response);
//    }
}
