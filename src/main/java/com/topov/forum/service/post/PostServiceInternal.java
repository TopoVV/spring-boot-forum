package com.topov.forum.service.post;

import com.topov.forum.model.Comment;
import com.topov.forum.service.interraction.AddComment;

public interface PostServiceInternal {
    void addComment(Comment comment);
}
