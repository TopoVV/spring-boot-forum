package com.topov.forum.service.user;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.model.Comment;
import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Post;
import com.topov.forum.repository.UserRepository;
import com.topov.forum.security.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class UserServiceImpl implements UserService, UserServiceInternal     {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(AuthenticationService authenticationService,
                           UserRepository userRepository) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void saveUser(ForumUser user) {
        log.debug("Creating a superuser");
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
