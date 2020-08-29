package com.topov.forum.service.user;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.model.Comment;
import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Post;
import com.topov.forum.model.Role;
import com.topov.forum.repository.UserRepository;
import com.topov.forum.security.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static com.topov.forum.model.Role.Roles;

@Log4j2
@Service
public class UserServiceImpl implements UserService, UserServiceInternal     {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    @Autowired
    public UserServiceImpl(AuthenticationService authenticationService,
                           UserRepository userRepository,
                           UserFactory userFactory) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    @Override
    @Transactional
    public void createRegularUser(RegistrationRequest registrationRequest) {
        log.debug("Creating a regular user");
        final ForumUser user = userFactory.constructRegularUser(registrationRequest);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void createSuperuser(RegistrationRequest registrationRequest) {
        log.debug("Creating a superuser");
        final ForumUser user = userFactory.constructSuperuser(registrationRequest);
        userRepository.save(user);
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

    @Override
    public void addComment(Comment comment) {
        log.debug("Adding new comment to user's comments collection {}", comment);
        final Long currentUserId = authenticationService.getCurrentUserId();
        userRepository.findById(currentUserId)
            .ifPresentOrElse(
                user -> user.addComment(comment),
                () -> { throw new RuntimeException("User not found"); }
            );
    }

    @Override
    public void addPost(Post post) {
        log.debug("Adding new comment to user's posts collection {}", post);
        final Long currentUserId = authenticationService.getCurrentUserId();
        userRepository.findById(currentUserId)
            .ifPresentOrElse(
                user -> user.addPost(post),
                () -> { throw new RuntimeException("User not found"); }
            );
    }
}
