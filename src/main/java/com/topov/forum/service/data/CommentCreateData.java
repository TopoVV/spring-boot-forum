package com.topov.forum.service.data;

import com.topov.forum.dto.request.comment.CommentCreateRequest;
import lombok.Getter;

@Getter
public class CommentCreateData {
    private final String text;
    private final Long postId;

    public CommentCreateData(CommentCreateRequest commentCreateRequest, Long postId) {
        this.text = commentCreateRequest.getText();
        this.postId = postId;
    }
}
