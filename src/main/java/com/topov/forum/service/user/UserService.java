package com.topov.forum.service.user;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.model.ForumUser;

public interface UserService {
    void saveUser(ForumUser user);
    void enableUser(String username);
}
