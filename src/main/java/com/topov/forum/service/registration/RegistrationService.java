package com.topov.forum.service.registration;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.dto.request.registration.SuperuserRegistrationRequest;
import com.topov.forum.dto.response.registration.RegistrationResponse;
import org.springframework.http.ResponseEntity;

public interface RegistrationService {
    RegistrationResponse registerRegularUser(RegistrationRequest registrationRequest);
    RegistrationResponse registerSuperuser(SuperuserRegistrationRequest registrationRequest);
}
