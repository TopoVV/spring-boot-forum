package com.topov.forum.service.registration;

<<<<<<< HEAD
import com.topov.forum.dto.result.OperationResult;
=======
import com.topov.forum.dto.OperationResult;
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;

public interface RegistrationService {
    OperationResult registerRegularUser(RegistrationRequest registrationRequest);
    OperationResult registerSuperuser(SuperuserRegistrationRequest registrationRequest);
}
