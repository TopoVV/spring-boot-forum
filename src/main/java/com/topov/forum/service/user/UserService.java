package com.topov.forum.service.user;

import com.topov.forum.dto.request.registration.RegistrationRequest;

public interface UserService {
    void createRegularUser(RegistrationRequest registrationRequest);
    void createSuperuser(RegistrationRequest registrationRequest);
    void enableUser(String username);
}
