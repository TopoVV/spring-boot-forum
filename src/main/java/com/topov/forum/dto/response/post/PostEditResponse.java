package com.topov.forum.dto.response.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.topov.forum.dto.PostDto;
import com.topov.forum.dto.response.OperationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PostEditResponse extends OperationResponse {
    private static final String POST_SUCCESSFULLY_EDITED = "The post has been successfully edited";
    private static final String POST_NOT_EDITED = "The post has not been edited";

    public static PostEditResponse notEdited() {
        return new PostEditResponse(POST_NOT_EDITED);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PostDto postDto;

    public PostEditResponse(PostDto postDto) {
        super(POST_SUCCESSFULLY_EDITED);
        this.postDto = postDto;
    }

    private PostEditResponse(String message) {
        super(message);
    }
}
