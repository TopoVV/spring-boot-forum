package com.topov.forum.dto;

import lombok.Getter;

@Getter
public class SimpleError extends Errors {
    private final String description;

    public SimpleError(String description) {
        this.description = description;
    }
}
