package com.topov.forum.service;

import com.topov.forum.dto.request.RegistrationRequest;

public interface RegistrationService {
    void registerUser(RegistrationRequest registrationRequest);
    boolean confirmRegistration(String token);
}
