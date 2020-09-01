package com.topov.forum.service.user;

import com.topov.forum.model.ForumUser;

public interface UserService {
    void saveUser(ForumUser user);
    void enableUser(String username);

    ForumUser findUser(Long userId);
}
