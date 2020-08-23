package com.topov.forum.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Data
@NoArgsConstructor
public class CreatePostResponse {
    private String message;
    private Long createdPostId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, List<String>> inputErrors;

    public CreatePostResponse(String message, BindingResult bindingResult) {
        this.message = message;
        this.inputErrors = bindingResult.getFieldErrors()
            .stream()
            .collect(groupingBy(
                FieldError::getField,
                mapping(FieldError::getDefaultMessage, toList())
            ));
    }

    public CreatePostResponse(String message, Long postId) {
        this.message = message;
        this.createdPostId = postId;
    }
}
