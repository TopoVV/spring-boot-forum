package com.topov.forum.controller.advice;

import com.topov.forum.controller.PostController;
import com.topov.forum.exception.PostException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice(assignableTypes = { PostController.class })
public class PostControllerAdvice {
    @ExceptionHandler(value = PostException.class)
    public ResponseEntity<String> handlePostException(PostException e) {
        log.error("Post exception", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
