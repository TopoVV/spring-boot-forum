package com.topov.forum.exception;

public class CommentException extends RuntimeException {
    public CommentException(String message, Throwable cause) {
        super(message, cause);
    }
}
