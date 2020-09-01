package com.topov.forum.service.security;

import com.topov.forum.model.Comment;
import com.topov.forum.model.ForumUser;
import com.topov.forum.repository.CommentRepository;
import com.topov.forum.security.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@Component
public class CommentServiceSecurity {
    private final AuthenticationService authenticationService;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceSecurity(AuthenticationService authenticationService, CommentRepository commentRepository) {
        this.authenticationService = authenticationService;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public boolean checkOwnership(Long commentId) {
        log.debug("Check the user rights to edit the comment");
        final Long currentUserId = authenticationService.getCurrentUserId();
        return commentRepository.findById(commentId)
            .map(Comment::getCreator)
            .map(ForumUser::getUserId)
            .map(currentUserId::equals)
            .orElseThrow(() -> {
                log.error("Comment with id={} doesn't exist", commentId);
                return new ResponseStatusException(HttpStatus.BAD_REQUEST, "The comment doesn't exist");
            });
    }
}
