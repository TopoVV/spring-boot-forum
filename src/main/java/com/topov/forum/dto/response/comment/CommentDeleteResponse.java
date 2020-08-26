package com.topov.forum.dto.response.comment;

import com.topov.forum.dto.response.OperationResponse;
import com.topov.forum.dto.response.post.PostDeleteResponse;

public class CommentDeleteResponse extends OperationResponse {
    private static final String COMMENT_SUCCESSFULLY_DELETED = "The comment has been deleted";

    public static CommentDeleteResponse deleted() {
        return new CommentDeleteResponse(COMMENT_SUCCESSFULLY_DELETED);
    }

    private CommentDeleteResponse(String message) {
        super(message);
    }
}
