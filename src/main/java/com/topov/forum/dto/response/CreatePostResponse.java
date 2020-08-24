package com.topov.forum.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.topov.forum.dto.PostDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreatePostResponse extends OperationResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PostDto postDto;

    public CreatePostResponse(PostDto postDto) {
        super("The post has been created");
        this.postDto = postDto;
    }
}
