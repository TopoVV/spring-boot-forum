package com.topov.forum.service.user;

import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Post;
import com.topov.forum.repository.UserRepository;
import com.topov.forum.security.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<<<<<<<HEAD
=======
    >>>>>>>tmp-1

@Log4j2
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
<<<<<<< HEAD
    public UserServiceImpl(AuthenticationService authenticationService,
                           UserRepository userRepository) {
        this.authenticationService = authenticationService;
=======
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
>>>>>>> tmp-1
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
<<<<<<< HEAD
    public void addComment(Long creatorId, Comment comment) {
        userRepository.findById(creatorId)
            .ifPresentOrElse(
                user -> user.addComment(comment),
                () -> { throw new RuntimeException("User not found"); }
            );
    }

    @Override
    public void addPost(Long creatorId, Post post) {
        log.debug("Adding new comment to user's posts collection {}", creatorId);
        userRepository.findById(creatorId)
            .ifPresentOrElse(
                user -> user.addPost(post),
                () -> { throw new RuntimeException("User not found"); }
            );
=======
    public ForumUser findUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(EntityNotFoundException::new);
>>>>>>> tmp-1
    }
}
