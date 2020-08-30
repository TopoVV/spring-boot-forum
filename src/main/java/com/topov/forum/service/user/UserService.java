package com.topov.forum.service.user;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.model.ForumUser;

public interface UserService {
    void createRegularUser(RegistrationRequest registrationRequest);
    void createSuperuser(RegistrationRequest registrationRequest);
    void enableUser(String username);

    ForumUser findUser(Long userId);
}
