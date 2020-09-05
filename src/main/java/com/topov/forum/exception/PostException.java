package com.topov.forum.exception;

public class PostException extends RuntimeException {
    public PostException(String message, Throwable cause) {
        super(message, cause);
    }
}
