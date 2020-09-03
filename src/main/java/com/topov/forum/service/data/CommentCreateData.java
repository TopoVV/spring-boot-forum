package com.topov.forum.service.data;

import com.topov.forum.dto.request.comment.CommentCreateRequest;
import lombok.Getter;

@Getter
public class CommentCreateData {
    private final String text;
    private final Long targetPostId;

    public CommentCreateData(CommentCreateRequest commentCreateRequest, Long targetPostId) {
        this.text = commentCreateRequest.getText();
        this.targetPostId = targetPostId;
    }
}
