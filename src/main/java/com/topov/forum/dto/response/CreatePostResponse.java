package com.topov.forum.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.topov.forum.dto.PostDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CreatePostResponse extends OperationResponse {
    private static final String POST_SUCCESSFULLY_CREATED = "The post has been successfully created";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PostDto postDto;

    public CreatePostResponse(PostDto postDto) {
        super(POST_SUCCESSFULLY_CREATED);
        this.postDto = postDto;
    }
}
