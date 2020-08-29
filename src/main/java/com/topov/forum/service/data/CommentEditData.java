package com.topov.forum.service.data;

import com.topov.forum.dto.request.comment.CommentEditRequest;
import lombok.Getter;

@Getter
public class CommentEditData {
    private final Long commentId;
    private final Long postId;
    private final String newText;

    public CommentEditData(CommentEditRequest commentEditRequest, Long postId, Long commentId) {
        this.commentId = commentId;
        this.postId = postId;
        this.newText = commentEditRequest.getText();
    }
}
