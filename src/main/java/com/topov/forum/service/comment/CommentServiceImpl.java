package com.topov.forum.service.comment;

import com.topov.forum.dto.CommentDto;
import com.topov.forum.dto.response.comment.CommentCreateResponse;
import com.topov.forum.dto.response.comment.CommentDeleteResponse;
import com.topov.forum.dto.response.comment.CommentEditResponse;
import com.topov.forum.exception.CommentException;
import com.topov.forum.mapper.CommentMapper;
import com.topov.forum.model.Comment;
import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Post;
import com.topov.forum.model.Status;
import com.topov.forum.repository.CommentRepository;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.repository.UserRepository;
import com.topov.forum.security.AuthenticationService;
import com.topov.forum.service.data.CommentCreateData;
import com.topov.forum.service.data.CommentEditData;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@Log4j2
@Service
public class CommentServiceImpl implements CommentService {
    private final AuthenticationService authenticationService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(AuthenticationService authenticationService,
                              CommentRepository commentRepository,
                              UserRepository userRepository,
                              PostRepository postRepository,
                              CommentMapper commentMapper) {
        this.authenticationService = authenticationService;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    @Transactional
    public CommentCreateResponse createComment(CommentCreateData commentCreateData) {
        log.debug("Creating comment: {}", commentCreateData);
        try {
            final Comment newComment = new Comment();
            newComment.setText(commentCreateData.getText());
            newComment.setStatus(Status.ACTIVE);

            final Long targetPostId = commentCreateData.getTargetPostId();
            final Long creatorId = authenticationService.getCurrentUserId();

             final ForumUser creator = userRepository.findById(creatorId)
                .orElseThrow(EntityNotFoundException::new);
            final Post post = postRepository.findById(targetPostId)
                .orElseThrow(EntityNotFoundException::new);

            creator.addComment(newComment);
            post.addComment(newComment);

            final Comment savedComment = commentRepository.save(newComment);
            final CommentDto commentDto = commentMapper.toDto(savedComment);
            return new CommentCreateResponse(commentDto);
        } catch (RuntimeException e) {
            log.error("Error creating comment", e);
            throw new CommentException("Cannot create comment", e);
        }
    }


    @Override
    @Transactional
    @PreAuthorize("@commentServiceSecurity.checkOwnership(#commentEditData.commentId) or hasRole('SUPERUSER')")
    public CommentEditResponse editComment(CommentEditData commentEditData) {
        final Long commentId = commentEditData.getCommentId();

        return commentRepository.findById(commentId)
            .map(comment -> doEditComment(commentEditData, comment))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
    }

    private CommentEditResponse doEditComment(CommentEditData editCommentRequest, Comment comment) {
        if(comment.isActive()) {
            comment.setText(editCommentRequest.getNewText());
            final CommentDto commentDto = commentMapper.toDto(comment);
            return new CommentEditResponse(commentDto);
        }
        return CommentEditResponse.commentDisabled();
    }


    @Override
    @Transactional
    @PreAuthorize("@commentServiceSecurity.checkOwnership(#commentId) or hasRole('SUPERUSER')")
    public CommentDeleteResponse deleteComment(Long commentId) {
        log.debug("Deleting comment with id={}", commentId);
        return commentRepository.findById(commentId)
            .map(this::doDelete)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }

    private CommentDeleteResponse doDelete(Comment comment) {
        if(comment.isActive()) {
            comment.disable();
        }
        return CommentDeleteResponse.deleted();
    }
}
