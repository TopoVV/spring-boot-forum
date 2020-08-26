package com.topov.forum.dto.response.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.topov.forum.dto.CommentDto;
import com.topov.forum.dto.response.OperationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommentEditResponse extends OperationResponse {
    private static final String COMMENT_SUCCESSFULLY_EDITED = "The comment has been successfully edited";
    private static final String COMMENT_NOT_EDITED = "The comment has not been edited. Reason %s";
    private static final String COMMENT_IS_DISABLED = "The comment is inactive";

    public static CommentEditResponse commentDisabled() {
        return new CommentEditResponse(COMMENT_IS_DISABLED);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CommentDto commentDto;

    public CommentEditResponse(CommentDto commentDto) {
        super(COMMENT_SUCCESSFULLY_EDITED);
        this.commentDto = commentDto;
    }

    private CommentEditResponse(String reason) {
        super(String.format(COMMENT_NOT_EDITED, reason));
    }

}
