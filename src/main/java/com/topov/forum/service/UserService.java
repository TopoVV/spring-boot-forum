package com.topov.forum.service;

import com.topov.forum.model.ForumUser;

public interface UserService {
    void addRegularUser(ForumUser user);
    void enableUser(String username);
}
