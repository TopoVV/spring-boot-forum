package com.topov.forum.service.user;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.model.ForumUser;

public interface UserFactory {
    ForumUser constructRegularUser(RegistrationRequest registrationRequest);
    ForumUser constructSuperuser(RegistrationRequest registrationRequest);
}
