package com.topov.forum.service.registration;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;
import com.topov.forum.dto.result.OperationResult;

public interface RegistrationService {
    OperationResult registerRegularUser(RegistrationRequest registrationRequest);
    OperationResult registerSuperuser(SuperuserRegistrationRequest registrationRequest);
}
