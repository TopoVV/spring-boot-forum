package com.topov.forum.service;

import com.topov.forum.dto.request.RegistrationRequest;
import com.topov.forum.dto.request.SuperuserRegistrationRequest;
import com.topov.forum.dto.response.AccountConfirmation;

public interface RegistrationService {
    void registerUser(RegistrationRequest registrationRequest);
    void registerUser(SuperuserRegistrationRequest registrationRequest);
    AccountConfirmation confirmAccount(String token);
}
