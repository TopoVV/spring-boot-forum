package com.topov.forum.service;

import com.topov.forum.dto.request.RegistrationRequest;
import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Role;
import com.topov.forum.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.topov.forum.model.Role.Roles;

@Log4j2
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void createRegularUser(RegistrationRequest registrationRequest) {
        final ForumUser newUser = assembleUser(registrationRequest);
        newUser.addRole(new Role(Roles.USER));
        newUser.setEnabled(false);
        userRepository.save(newUser);
    }

    @Override
    @Transactional
    public void createSuperuser(RegistrationRequest registrationRequest) {
        final ForumUser newUser = assembleUser(registrationRequest);
        newUser.addRole(new Role(Roles.USER));
        newUser.addRole(new Role(Roles.ADMIN));
        newUser.addRole(new Role(Roles.SUPERUSER));
        newUser.setEnabled(true);
        userRepository.save(newUser);
    }

    @Override
    @Transactional
    public void enableUser(String username) {
        log.debug("Enabling user: {}", username);
        userRepository.findByUsername(username)
            .stream()
            .peek(ForumUser::enable)
            .findFirst()
            .orElseThrow(() -> {
                log.error("User not found");
                return new RuntimeException("The user doesn't exist");
            });
    }

    private ForumUser assembleUser(RegistrationRequest registrationRequest) {
        final ForumUser forumUser = new ForumUser();
        forumUser.setUsername(registrationRequest.getUsername());
        forumUser.setEmail(registrationRequest.getEmail());
        forumUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        return forumUser;
    }
}
