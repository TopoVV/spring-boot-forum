package com.topov.forum.service.security;

import com.topov.forum.model.Comment;
import com.topov.forum.model.ForumUser;
import com.topov.forum.repository.CommentRepository;
import com.topov.forum.security.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
                return new RuntimeException(String.format("Post (id = %d) not found", commentId));
            });
    }
}
