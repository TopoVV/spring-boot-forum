package com.topov.forum.service.registration;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;

public interface RegistrationService {
    void registerRegularUser(RegistrationRequest registrationRequest);
    void registerSuperuser(SuperuserRegistrationRequest registrationRequest);
}
