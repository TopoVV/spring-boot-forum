package com.topov.forum.dto.response;

import com.topov.forum.dto.CommentDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CreateCommentResponse extends OperationResponse {
    private static final String COMMENT_SUCCESSFULLY_CREATED = "The comment has been successfully created";

    private CommentDto commentDto;

    public CreateCommentResponse(CommentDto commentDto) {
        super(COMMENT_SUCCESSFULLY_CREATED);
        this.commentDto = commentDto;
    }

}
