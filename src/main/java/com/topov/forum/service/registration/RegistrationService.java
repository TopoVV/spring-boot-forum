package com.topov.forum.service.registration;

import com.topov.forum.dto.OperationResult;
import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;

public interface RegistrationService {
    OperationResult registerRegularUser(RegistrationRequest registrationRequest);
    OperationResult registerSuperuser(SuperuserRegistrationRequest registrationRequest);
}
