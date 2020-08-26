package com.topov.forum.dto.response.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.topov.forum.dto.PostDto;
import com.topov.forum.dto.response.OperationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostCreateResponse extends OperationResponse {
    private static final String POST_SUCCESSFULLY_CREATED = "The post has been successfully created";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PostDto postDto;

    public PostCreateResponse(PostDto postDto) {
        super(POST_SUCCESSFULLY_CREATED);
        this.postDto = postDto;
    }
}
