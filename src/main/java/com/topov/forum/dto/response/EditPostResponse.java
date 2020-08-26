package com.topov.forum.dto.response;

import com.topov.forum.dto.PostDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EditPostResponse extends OperationResponse {
    private static final String POST_SUCCESSFULLY_EDITED = "The post has been successfully edited";
    private PostDto postDto;

    public EditPostResponse(PostDto postDto) {
        super(POST_SUCCESSFULLY_EDITED);
        this.postDto = postDto;
    }
}
