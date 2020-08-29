package com.topov.forum.service.user;

import com.topov.forum.model.Comment;
import com.topov.forum.model.Post;
import com.topov.forum.service.interraction.AddComment;
import com.topov.forum.service.interraction.AddPost;

public interface UserServiceInternal {
    void addComment(Comment comment);
    void addPost(Post post);
}
