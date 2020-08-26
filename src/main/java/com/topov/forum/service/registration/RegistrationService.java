package com.topov.forum.service.registration;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;
import com.topov.forum.dto.response.registration.AccountConfirmation;
import com.topov.forum.dto.response.registration.RegistrationResponse;

public interface RegistrationService {
    RegistrationResponse registerRegularUser(RegistrationRequest registrationRequest);
    RegistrationResponse registerSuperuser(SuperuserRegistrationRequest registrationRequest);
    AccountConfirmation confirmAccount(String token);
}
