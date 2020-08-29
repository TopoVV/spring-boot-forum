package com.topov.forum.service.user;

import com.topov.forum.model.Comment;
import com.topov.forum.model.Post;

public interface UserServiceInternal {
    void addComment(Comment comment);
    void addPost(Post post);
}
