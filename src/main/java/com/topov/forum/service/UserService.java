package com.topov.forum.service;

import com.topov.forum.model.ForumUser;

public interface UserService {
    void saveRegularUser(ForumUser user);
    void saveSuperuser(ForumUser user);
    void enableUser(String username);
}
