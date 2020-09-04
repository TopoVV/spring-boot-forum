package com.topov.forum.dto.request.post;

import com.topov.forum.validation.post.constraint.UniqueTitle;
import com.topov.forum.validation.post.group.PostCreationChecks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {
    @NotEmpty(message = "A post must contain text")
    @Size(message = "The post text must not contain more than 2500 symbols", max = 2500)
    private String text;
    @UniqueTitle(groups = PostCreationChecks.class)
    @NotEmpty(message = "A post must have title")
    private String title;
}
