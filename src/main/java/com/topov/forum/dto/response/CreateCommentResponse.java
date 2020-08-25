package com.topov.forum.dto.response;

import com.topov.forum.dto.CommentDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CreateCommentResponse extends OperationResponse {
    private CommentDto commentDto;

    public CreateCommentResponse(CommentDto commentDto) {
        super("Comment created");
        this.commentDto = commentDto;
    }

}
