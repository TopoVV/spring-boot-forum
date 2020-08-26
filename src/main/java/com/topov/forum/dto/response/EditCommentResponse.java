package com.topov.forum.dto.response;

import com.topov.forum.dto.CommentDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EditCommentResponse extends OperationResponse {
    private static final String COMMENT_SUCCESSFULLY_EDITED = "The comment has been successfully edited";

    private CommentDto commentDto;

    public EditCommentResponse(CommentDto commentDto) {
        super(COMMENT_SUCCESSFULLY_EDITED);
        this.commentDto = commentDto;
    }
}
