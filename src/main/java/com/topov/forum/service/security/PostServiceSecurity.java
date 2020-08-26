package com.topov.forum.service.security;

import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Post;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.security.AuthenticationService;
import com.topov.forum.security.ForumUserDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@Component
public class PostServiceSecurity {
    private final AuthenticationService authenticationService;
    private final PostRepository postRepository;

    @Autowired
    public PostServiceSecurity(AuthenticationService authenticationService, PostRepository postRepository) {
        this.authenticationService = authenticationService;
        this.postRepository = postRepository;
    }

    @Transactional
    public boolean checkOwnership(Long postId) {
        log.debug("Check the user rights to edit the post");
        final ForumUserDetails authenticatedUser = (ForumUserDetails) authenticationService.getAuthenticatedUser();
        return postRepository.findById(postId)
            .map(Post::getCreator)
            .map(ForumUser::getUserId)
            .map(creatorId -> authenticatedUser.getUserId().equals(creatorId))
            .orElseThrow(() -> {
                log.error("Post with id={} doesnt exist", postId);
                final String notFoundMessage = String.format("Post (id = %d) not found", postId);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, notFoundMessage);
            });
    }
}
