package com.topov.forum.service.user;

import com.topov.forum.service.interraction.AddComment;
import com.topov.forum.service.interraction.AddPost;

public interface UserServiceInternal {
    void addComment(AddComment addComment);
    void addPost(AddPost addPost);
}
