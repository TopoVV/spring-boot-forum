package com.topov.forum.service;

import com.topov.forum.dto.request.RegistrationRequest;

public interface UserService {
    void createRegularUser(RegistrationRequest registrationRequest);
    void createSuperuser(RegistrationRequest registrationRequest);
    void enableUser(String username);
}
