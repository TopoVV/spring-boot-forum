package com.topov.forum.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class ApiResponse {
    private final String message;
    private final String status;

    protected ApiResponse(String message, String status) {
        this.message = message;
        this.status = status;
    }

    protected ApiResponse(String status) {
        this.message = "";
        this.status = status;
    }
}
