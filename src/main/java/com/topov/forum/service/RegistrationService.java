package com.topov.forum.service;

import com.topov.forum.dto.request.RegistrationRequest;
import com.topov.forum.dto.request.SuperuserRegistrationRequest;
import com.topov.forum.dto.response.AccountConfirmation;
import com.topov.forum.dto.response.RegistrationResponse;

public interface RegistrationService {
    void registerUser(RegistrationRequest registrationRequest);
    RegistrationResponse registerUser(SuperuserRegistrationRequest registrationRequest);
    AccountConfirmation confirmAccount(String token);
}
