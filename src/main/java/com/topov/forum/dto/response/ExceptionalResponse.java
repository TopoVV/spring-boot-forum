package com.topov.forum.dto.response;


import lombok.Data;

@Data
public class ExceptionalResponse {
    private String message;
    private String description;

    public ExceptionalResponse(String message, String description) {
        this.message = message;
        this.description = description;
    }
}
