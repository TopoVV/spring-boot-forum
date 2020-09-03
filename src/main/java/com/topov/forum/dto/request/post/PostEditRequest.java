package com.topov.forum.dto.request.post;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class PostEditRequest {
    @NotEmpty(message = "A post must contain text")
    @Size(message = "The post text must not contain more than 2500 symbols", max = 2500)
    private String text;
    @NotEmpty(message = "A post must have title")
    private String newTitle;
    @NotEmpty
    private String oldTitle;
}
