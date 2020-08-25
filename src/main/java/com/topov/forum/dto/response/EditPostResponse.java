package com.topov.forum.dto.response;

import com.topov.forum.dto.PostDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
public class EditPostResponse extends OperationResponse {
    private PostDto postDto;

    public EditPostResponse(PostDto postDto) {
        super("Post edited");
        this.postDto = postDto;
    }
}
