package com.topov.forum.dto.response.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.topov.forum.dto.CommentDto;
import com.topov.forum.dto.response.OperationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommentCreateResponse extends OperationResponse {
    private static final String COMMENT_SUCCESSFULLY_CREATED = "The comment has been successfully created";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CommentDto commentDto;

    public CommentCreateResponse(CommentDto commentDto) {
        super(COMMENT_SUCCESSFULLY_CREATED);
        this.commentDto = commentDto;
    }

}
