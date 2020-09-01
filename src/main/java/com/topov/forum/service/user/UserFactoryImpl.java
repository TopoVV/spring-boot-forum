package com.topov.forum.service.user;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.topov.forum.model.Role.Roles;

@Service
public class UserFactoryImpl implements UserFactory {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserFactoryImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ForumUser constructRegularUser(RegistrationRequest registrationRequest) {
        final ForumUser newUser = defaultUser(registrationRequest);
        newUser.addRole(new Role(Roles.USER));
        newUser.setEnabled(false);
        return newUser;
    }

    @Override
    public ForumUser constructSuperuser(RegistrationRequest registrationRequest) {
        final ForumUser newUser = defaultUser(registrationRequest);
        newUser.addRole(new Role(Roles.USER));
        newUser.addRole(new Role(Roles.ADMIN));
        newUser.addRole(new Role(Roles.SUPERUSER));
        newUser.setEnabled(true);
        return newUser;
    }

    private ForumUser defaultUser(RegistrationRequest registration) {
        final String encodedPassword = passwordEncoder.encode(registration.getPassword());
        return new ForumUser(registration.getUsername(), encodedPassword, registration.getEmail());
    }
}
