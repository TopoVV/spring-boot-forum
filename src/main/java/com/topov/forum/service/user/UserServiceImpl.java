package com.topov.forum.service.user;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Role;
import com.topov.forum.repository.UserRepository;
import com.topov.forum.security.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static com.topov.forum.model.Role.*;


@Log4j2
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(AuthenticationService authenticationService,
                           UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public void createRegularUser(RegistrationRequest registrationRequest) {
        final String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());
        final ForumUser newUser = new ForumUser(registrationRequest.getUsername(), encodedPassword, registrationRequest.getEmail());
        newUser.addRole(new Role(Roles.USER));
        newUser.addRole(new Role(Roles.ADMIN));
        newUser.addRole(new Role(Roles.SUPERUSER));
        newUser.setEnabled(true);
        userRepository.save(newUser);
    }

    @Override
    @Transactional
    public void createSuperuser(RegistrationRequest registrationRequest) {
        log.debug("Creating a superuser");
        final String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());
        final ForumUser newUser = new ForumUser(registrationRequest.getUsername(), encodedPassword, registrationRequest.getEmail());
        newUser.addRole(new Role(Roles.USER));
        newUser.setEnabled(false);
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
            .orElseThrow(() -> new RuntimeException("The user doesn't exist"));
    }


    public ForumUser findUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(EntityNotFoundException::new);
    }
}
