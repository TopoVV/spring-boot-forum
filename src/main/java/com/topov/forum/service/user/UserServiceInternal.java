package com.topov.forum.service.user;

import com.topov.forum.model.Comment;
import com.topov.forum.model.Post;

public interface UserServiceInternal {
    void addComment(Long creatorId, Comment comment);
    void addPost(Long creatorId, Post post);
}
