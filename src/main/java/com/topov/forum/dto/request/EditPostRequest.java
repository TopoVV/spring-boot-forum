package com.topov.forum.dto.request;

import com.topov.forum.validation.constraint.UniqueTitle;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@UniqueTitle
public class EditPostRequest {
    @NotEmpty(message = "A post must contain text")
    @Size(message = "The post text must not contain more than 2500 symbols", max = 2500)
    private String text;
    @NotEmpty(message = "A post must have title")
    private String newTitle;
    @NotNull
    private Long postId;
    @NotEmpty
    private String oldTitle;
}
