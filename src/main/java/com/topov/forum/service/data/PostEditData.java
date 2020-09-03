package com.topov.forum.service.data;

import com.topov.forum.dto.request.post.PostEditRequest;
import lombok.Getter;

@Getter
public class PostEditData {
    private final String text;
    private final String oldTitle;
    private final String newTitle;
    private final Long postId;

    public PostEditData(PostEditRequest postEditRequest, Long postId) {
        this.text = postEditRequest.getText();
        this.newTitle = postEditRequest.getNewTitle();
        this.oldTitle = postEditRequest.getOldTitle();
        this.postId = postId;
    }
}
