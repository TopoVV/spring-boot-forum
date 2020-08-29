package com.topov.forum.service.post;

import com.topov.forum.model.Comment;

public interface PostServiceInternal {
    void addComment(Long targetPost, Comment comment);
}
