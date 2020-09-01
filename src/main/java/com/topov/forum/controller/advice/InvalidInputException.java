package com.topov.forum.controller.advice;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class InvalidInputException extends RuntimeException {
    private final BindingResult bindingResult;

    public InvalidInputException(String message, BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }
}
