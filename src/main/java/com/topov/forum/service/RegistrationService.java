package com.topov.forum.service;

import com.topov.forum.dto.request.RegistrationRequest;
import com.topov.forum.dto.request.SuperuserRegistrationRequest;

public interface RegistrationService {
    void registerUser(RegistrationRequest registrationRequest);
    void registerUser(SuperuserRegistrationRequest registrationRequest);
    boolean confirmRegistration(String token);
}
